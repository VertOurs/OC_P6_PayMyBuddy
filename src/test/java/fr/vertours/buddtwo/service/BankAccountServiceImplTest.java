package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.dto.AddBankDTO;
import fr.vertours.buddtwo.model.BankAccount;
import fr.vertours.buddtwo.model.Role;
import fr.vertours.buddtwo.model.User;
import fr.vertours.buddtwo.repository.BankAccountRepository;
import fr.vertours.buddtwo.security.MyUserDetails;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class BankAccountServiceImplTest {

    private final BankAccountRepository bankAccountRepository =
            mock(BankAccountRepository.class);

    private final BankAccountServiceImpl classUnderTest =
            new BankAccountServiceImpl(bankAccountRepository);


    private User returnGeorgeUser() {
        User george = new User();
        george.setId(1L);
        george.setFirstName("George");
        george.setLastName("Bidon");
        george.setEmail("george@gmail.com");
        george.setPassword("georgeTest");
        return george;
    }
    private MyUserDetails getMyUserDetailsOfGeorge() {
        User george = returnGeorgeUser();
        george.setRoleList(new ArrayList<>());
        george.getRoleList().add(new Role("USER"));
        return new MyUserDetails(
                george.getRoleList(), george.getPassword(),
                george.getEmail(), george);
    }

    private BankAccount returnBankAccount() {
        BankAccount bk = new BankAccount();
        bk.setId(10L);
        bk.setBic("bic");
        bk.setCustomizeName("bank");
        bk.setIban("iban");
        bk.setUser(returnGeorgeUser());
        return bk;
    }
    private AddBankDTO returnAddBankDTO() {
        AddBankDTO dto = new AddBankDTO();
        dto.setBic("bic");
        dto.setIban("bank");
        dto.setCustomizeName("iban");
        return dto;
    }
    @Test
    void saveBankAccountEmptyOp() {
        MyUserDetails mud = getMyUserDetailsOfGeorge();
        Optional<BankAccount> isAccountExist = Optional.empty();
        when(bankAccountRepository.findByUser(mud.getUser()))
                .thenReturn(isAccountExist);

        classUnderTest.saveBankAccount(returnAddBankDTO(), mud);

        verify(bankAccountRepository, times(1))
                .save(any(BankAccount.class));

    }

}