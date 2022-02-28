package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.configuration.MyUserDetails;
import fr.vertours.buddtwo.dto.BankingTransferDTO;
import fr.vertours.buddtwo.model.Transfer;
import fr.vertours.buddtwo.model.User;
import fr.vertours.buddtwo.repository.TransferRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static fr.vertours.buddtwo.constants.ApplicationConstants.BANKINGTRANSFER;
import static fr.vertours.buddtwo.constants.ApplicationConstants.feeAmount;

@Service
public class TransferServiceImpl implements HomeTransferService {

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
}
