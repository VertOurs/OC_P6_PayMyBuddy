package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.dto.AdminDTO;
import fr.vertours.buddtwo.dto.BankingTransferDTO;
import fr.vertours.buddtwo.dto.TransactionDTO;
import fr.vertours.buddtwo.dto.TransferDTO;
import fr.vertours.buddtwo.model.Transfer;
import fr.vertours.buddtwo.model.User;
import fr.vertours.buddtwo.repository.TransferRepository;
import fr.vertours.buddtwo.security.MyUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static fr.vertours.buddtwo.UtilUnitTestMethods.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransferServiceImplTest {

    private final TransferRepository repository = mock(TransferRepository.class);
    private final UserServiceImpl userService = mock(UserServiceImpl.class);

    private final TransferServiceImpl classUnderTest =
            new TransferServiceImpl(repository, userService);

    @Test
    void creditApplicationAccount() {
        BankingTransferDTO dto = returnBankingTransferDTO();
        MyUserDetails mud = getMyUserDetailsOfJean();
        User jean = returnJeanUser();
        when(userService.findByEmail(mud.getUsername())).thenReturn(jean);

        classUnderTest.creditApplicationAccount(dto, mud);

        verify(userService, times(1))
                .findByEmail(jean.getEmail());
        verify(repository, times(1))
                .save(any(Transfer.class));
        verify(userService, times(1))
                .updateUser(jean);
    }

    @Test //TODO mettre en place le test erreur.
    void debitApplicationAccount() {
        BankingTransferDTO dto = returnBankingTransferDTO();
        MyUserDetails mud = getMyUserDetailsOfJean();
        User jean = returnJeanUser();
        when(userService.findByEmail(mud.getUsername())).thenReturn(jean);

        classUnderTest.debitApplicationAccount(dto, mud);

        verify(userService, times(1))
                .findByEmail(jean.getEmail());
        verify(repository, times(1))
                .save(any(Transfer.class));
        verify(userService, times(1))
                .updateUser(jean);
    }

    @Test
    void findTransferDTO() {
        TransferDTO transferDTO = returnGetTransferDTOFriendList();
        User jean = returnJeanUser();
        MyUserDetails mud = getMyUserDetailsOfJean();
        Pageable pageable =  PageRequest.of(1,3);
        when(userService.findByEmail(mud.getUsername())).thenReturn(jean);

        TransferDTO dtoExpected = classUnderTest.findTransferDTO(mud, pageable);

        verify(userService, times(1))
                .findByEmail(jean.getEmail());
        assertEquals(dtoExpected.getConnections().get(0).getEmail(),
                transferDTO.getConnections().get(0).getEmail());


    }
    @Test
    void getTransactionDTOByUser() {
        User jean = returnJeanUser();
        User george = returnGeorgeUser();
        List<Transfer> transfers = returnListTransfer(jean, george);
        List<TransactionDTO> dtoExpected = returnListTransactionDTO(jean, george);
        when(repository.findAll()).thenReturn(transfers);


        List<TransactionDTO> dtoActual =
                classUnderTest.getTransactionDTOByUser(jean);

        verify(repository, times(1)).findAll();
        assertEquals(dtoExpected.get(0).getFirstName(),
                dtoActual.get(0).getFirstName());
        assertEquals(dtoExpected.get(1).getFirstName(),
                dtoActual.get(1).getFirstName());
        assertEquals(dtoExpected.get(2).getAmount(),
                dtoActual.get(2).getAmount());
        assertEquals(dtoExpected.get(3).getAmount(),
                dtoActual.get(3).getAmount());
    }



    @Test
    void makeTransfer() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        User jean = returnJeanUser();
        User george = returnGeorgeUser();
        TransferDTO dto = returnGetTransferDTOTransactionsList();
        Transfer transfer = returnTransfer(jean, george);
        when(userService.findByEmail(jean.getEmail())).thenReturn(jean);
        when(userService.findByEmail(george.getEmail())).thenReturn(george);
        when(repository.save(transfer)).thenReturn(transfer);

        classUnderTest.makeTransfer(dto, mud);

        verify(userService, times(2))
                .findByEmail(any(String.class));
        verify(userService, times(1))
                .findByEmail(jean.getEmail());
        verify(repository, times(1)).save(any(Transfer.class));

    }

    @Test
    void createAdminDTO() {
        User jean = returnJeanUser();
        User george = returnGeorgeUser();
        List<Transfer> transfers = returnListTransfer(jean, george);
        when(repository.findAll()).thenReturn(transfers);
        AdminDTO expectedDTO = returnAdminDTO();

        AdminDTO actualDTO = classUnderTest.createAdminDTO();

        verify(repository, times(1)).findAll();
        assertEquals(expectedDTO.getBalance(),actualDTO.getBalance());

    }
}