package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.configuration.MyUserDetails;
import fr.vertours.buddtwo.dto.*;
import fr.vertours.buddtwo.model.Transfer;
import fr.vertours.buddtwo.model.User;
import fr.vertours.buddtwo.repository.TransferRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static fr.vertours.buddtwo.constants.ApplicationConstants.BANKINGTRANSFER;
import static fr.vertours.buddtwo.constants.ApplicationConstants.feeAmount;
import static fr.vertours.buddtwo.dto.FriendDTO.getFriendDTOByUser;

@Service
public class TransferServiceImpl implements HomeTransferService, TransferService, AuthTransferService {

    private TransferRepository repository;
    private UserServiceImpl userService;

    public TransferServiceImpl(TransferRepository repository, UserServiceImpl userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void creditApplicationAccount(BankingTransferDTO dto, MyUserDetails myUD) {
        User user = userService.findByEmail(myUD.getUsername());
        user.setBuddyBalance(user.getBuddyBalance().add(dto.getAmount()));
        Transfer transfer = new Transfer(LocalDateTime.now(), dto.getAmount(), feeAmount(dto.getAmount()), BANKINGTRANSFER, myUD.getUser(), myUD.getUser());
        repository.save(transfer);
        userService.updateUser(user);
        myUD.setUser(user);
    }

    @Override
    @Transactional
    public void debitApplicationAccount(BankingTransferDTO dto, MyUserDetails myUD)  {
        User user = userService.findByEmail(myUD.getUsername());
        BigDecimal amountWithFee = dto.getAmount().add(feeAmount(dto.getAmount()));
        user.setBuddyBalance(user.getBuddyBalance().subtract(amountWithFee));
        if(isBalanceNegative(user.getBuddyBalance())) {
            //TODO gestion d'erreur
        }
        Transfer transfer = new Transfer(LocalDateTime.now(), dto.getAmount().negate(), feeAmount(dto.getAmount()), BANKINGTRANSFER, myUD.getUser(), myUD.getUser());
        repository.save(transfer);
        userService.updateUser(user);
        myUD.setUser(user);
    }

    private boolean isBalanceNegative(BigDecimal balance) {
        int result = balance.compareTo(BigDecimal.ZERO);
        return result == -1;
    }

    @Override
    @Transactional
    public TransferDTO findTransferDTO(MyUserDetails myUD, Pageable pageable) {
        TransferDTO transferDTO = new TransferDTO();
        User user = userService.findByEmail(myUD.getUsername());
        transferDTO.setConnections(
                friendListDTOByFriendList(user.getMyFriendList()));
        List<TransactionDTO> transactions = getTransactionDTOByUser(user);
        transferDTO.setTransactionDTOS(getPageableTransactionDTO(transactions, pageable));
        return transferDTO;
    }

    private Page<TransactionDTO> getPageableTransactionDTO(List<TransactionDTO> transactions, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<TransactionDTO> list1;
        if (transactions.size() < startItem) {
            list1 = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, transactions.size());
            list1 = transactions.subList(startItem, toIndex);
        }
        return new PageImpl<>(list1, PageRequest.of(currentPage, pageSize), transactions.size());
    }


    @Override
    public void saveTransfer(TransferDTO dto, MyUserDetails myUD) {
        for(TransactionDTO dtoT :dto.getTransactionDTOS()) {
            System.out.println(dtoT.getDescription());
        }
    }

    @Override
    @Transactional
    public void makeTransfer(TransferDTO dto, MyUserDetails myUD) {
        User user = userService.findByEmail(myUD.getUsername());
        User friend = userService.findByEmail(dto.getEmail());
        BigDecimal allAmount = dto.getAmount().add(feeAmount(dto.getAmount()));
        if (isBalanceNegative(user.getBuddyBalance().subtract(allAmount))) {
            //TODO gestion des erreurs
        }
        Transfer transfer = new Transfer();
        transfer.setAmount(dto.getAmount());
        transfer.setAmountFee(feeAmount(dto.getAmount()));
        transfer.setDateOfTransaction(LocalDateTime.now());
        transfer.setDescription(dto.getDescription());
        transfer.setReceiver(friend);
        transfer.setSender(user);
        repository.save(transfer);

        user.setBuddyBalance(user.getBuddyBalance().subtract(allAmount));
        friend.setBuddyBalance(friend.getBuddyBalance().add(dto.getAmount()));
        userService.updateUser(user);
    }

    private List<TransactionDTO> getTransactionDTOByUser(User user) {
        List<TransactionDTO> dtos = new ArrayList<>();
        List<Transfer> bigList = repository.findAll();

        for(Transfer t : bigList) {
            TransactionDTO dto = new TransactionDTO();
            if(t.getReceiver().equals(t.getSender()) && t.getReceiver().equals(user)) {
                dto.setFirstName(t.getDescription());
                if(isBalanceNegative(t.getAmount())) {
                    dto.setDescription("DEBIT");
                } else {
                    dto.setDescription("CREDIT");
                }
                dto.setAmount(t.getAmount());
                dtos.add(dto);
            } else if(t.getReceiver().equals(user)) {
                dto.setFirstName(t.getSender().getFirstName());
                dto.setDescription(t.getDescription());
                dto.setAmount(t.getAmount());
                dtos.add(dto);
            } else if (t.getSender().equals(user)) {
                dto.setFirstName(t.getReceiver().getFirstName());
                dto.setDescription(t.getDescription());
                dto.setAmount(t.getAmount().negate());
                dtos.add(dto);
            }
        }
        return dtos;
    }
    private List<FriendDTO> friendListDTOByFriendList(List<User> friendList) {
        List<FriendDTO> list = new ArrayList<>();
        for(User u : friendList) {
            list.add(getFriendDTOByUser(u));
        }
        return list;
    }

    @Override
    public AdminDTO createAdminDTO() {
        List<Transfer> transfers = repository.findAll();
        BigDecimal totalfee = BigDecimal.ZERO;
        AdminDTO dto = new AdminDTO();
        for(Transfer t : transfers) {
            totalfee = totalfee.add(t.getAmountFee());
        }
        dto.setBalance(totalfee.toString());
        return dto;
    }
}
