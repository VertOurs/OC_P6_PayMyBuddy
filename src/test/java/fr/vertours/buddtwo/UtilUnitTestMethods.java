package fr.vertours.buddtwo;

import fr.vertours.buddtwo.dto.*;
import fr.vertours.buddtwo.model.BankAccount;
import fr.vertours.buddtwo.model.Role;
import fr.vertours.buddtwo.model.Transfer;
import fr.vertours.buddtwo.model.User;
import fr.vertours.buddtwo.security.MyUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static fr.vertours.buddtwo.constants.ApplicationConstants.*;

public class UtilUnitTestMethods {

    public static User returnGeorgeUser() {
        User george = new User();
        george.setId(1L);
        george.setFirstName("George");
        george.setLastName("Bidon");
        george.setEmail("george@gmail.com");
        george.setPassword("georgeTest");
        return george;
    }
    public static User returnKateUser() {
        User kate = new User();
        kate.setId(2L);
        kate.setFirstName("Kate");
        kate.setLastName("Fake");
        kate.setEmail("kate@gmail.com");
        return kate;
    }
    public static User returnJeanUser() {
        User jean = new User();
        jean.setId(3L);
        jean.setFirstName("Jean");
        jean.setLastName("Faux");
        jean.setEmail("jean@gmail.com");
        jean.setPassword("jeanTest");
        jean.getMyFriendList().add(returnGeorgeUser());
        jean.setBuddyBalance(jean.getBuddyBalance()
                .add(new BigDecimal("80").setScale(2)));
        jean.setBankAccount(returnBankAccount());
        return jean;
    }
    public static MyUserDetails getMyUserDetailsOfJean() {
        User jean = returnJeanUser();
        jean.setRoleList(new ArrayList<>());
        jean.getRoleList().add(new Role("USER"));
        return new MyUserDetails(
                jean.getRoleList(), jean.getPassword(),
                jean.getEmail(), jean);
    }

    public static BankAccount returnBankAccount() {
        BankAccount bk = new BankAccount();
        bk.setId(10L);
        bk.setBic("bic");
        bk.setCustomizeName("bank");
        bk.setIban("iban");
        bk.setUser(returnGeorgeUser());
        return bk;
    }
    public static AddBankDTO returnAddBankDTO() {
        AddBankDTO dto = new AddBankDTO();
        dto.setBic("bic");
        dto.setIban("bank");
        dto.setCustomizeName("iban");
        return dto;
    }
    public static User returnAdminUserInDB() {
        User admin = new User();
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setEmail("admin@mail.com");
        admin.setPassword("admin");
        return admin;
    }
    public static RegistrationDTO returnRegDTO() {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setFirstName("Harry");
        dto.setLastName("False");
        dto.setEmail("harry@gmail.com");
        dto.setPassword("test");
        dto.setConfirmation("test");
        return dto;
    }

    public static ChangePasswordDTO returnChangePasswordDTO() {
        ChangePasswordDTO dto = new ChangePasswordDTO();
        dto.setNewPassword("new");
        dto.setNewPasswordConfirmation("new");
        dto.setOldPassword("jeanTest");
        return dto;
    }

    public static MyUserDetails getMyUserDetailsOfGeorge() {
        User george = returnGeorgeUser();
        george.setRoleList(new ArrayList<>());
        george.getRoleList().add(new Role("USER"));
        return new MyUserDetails(
                george.getRoleList(), george.getPassword(),
                george.getEmail(), george);
    }

    public static BankingTransferDTO returnBankingTransferDTO() {
        BankingTransferDTO dto = new BankingTransferDTO();
        dto.setAmount(new BigDecimal("50.00").setScale(2));
        return dto;
    }
    public static TransferDTO returnGetTransferDTOFriendList() {
        TransferDTO dto = new TransferDTO();
        dto.getConnections().add(returnFriendDTO(returnGeorgeUser()));
        dto.getConnections().add(returnFriendDTO(returnKateUser()));

        return dto;
    }
    public static FriendDTO returnFriendDTO(User user) {
        FriendDTO dto = new FriendDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        return dto;
    }
    public static TransferDTO returnGetTransferDTOTransactionsList() {
        TransferDTO dto = new TransferDTO();
        dto.getConnections().add(returnFriendDTO(returnGeorgeUser()));
        dto.getConnections().add(returnFriendDTO(returnKateUser()));
        dto.setEmail(returnGeorgeUser().getEmail());
        dto.setDescription("Poste");
        dto.setAmount(new BigDecimal("5").setScale(2));
        dto.setTransactionDTOS(returnPageTransactionDTOS());

        return dto;
    }
    public static Page<TransactionDTO> returnPageTransactionDTOS() {
        List<TransactionDTO> list =
                returnListTransactionDTO(returnJeanUser(), returnGeorgeUser());
        Pageable pageable = PageRequest.of(0, 2);

        return new PageImpl<>(list, pageable, list.size());
    }

    public static Transfer returnTransfer(User user, User user1) {
        BigDecimal amount = new BigDecimal("5").setScale(2);
        return new Transfer(LocalDateTime.now(),
                amount, feeAmount(amount), "Poste", user, user1);
    }

    public static List<Transfer> returnListTransfer(User user, User user1) {
        List<Transfer> transfers = new ArrayList<>();
        BigDecimal amount = new BigDecimal("50").setScale(2);

        Transfer t1 = new Transfer(LocalDateTime.now(),
                amount, feeAmount(amount), CREDIT, user, user);
        transfers.add(t1);

        amount = new BigDecimal("-10").setScale(2);
        Transfer t2 = new Transfer(LocalDateTime.now(),
                amount, feeAmount(amount), DEBIT, user, user);
        transfers.add(t2);

        amount = new BigDecimal("5").setScale(2);
        Transfer t3 = new Transfer(LocalDateTime.now(),
                amount, feeAmount(amount), "Restau", user, user1);
        transfers.add(t3);

        amount = new BigDecimal("4").setScale(2);
        Transfer t4 = new Transfer(LocalDateTime.now(),
                amount, feeAmount(amount), "LaserGame", user1, user);
        transfers.add(t4);

        return transfers;
    }
    public static AdminDTO returnAdminDTO() {
        List<Transfer> transfers = returnListTransfer(
                returnJeanUser(), returnGeorgeUser());
        BigDecimal totalfee = BigDecimal.ZERO;
        AdminDTO dto = new AdminDTO();
        for (Transfer t : transfers) {
            totalfee = totalfee.add(t.getAmountFee());
        }
        dto.setBalance(totalfee.toString());

        return dto;
    }
    public static List<TransactionDTO> returnListTransactionDTO(User user,
                                                                User user1) {
        List<TransactionDTO> dtos = new ArrayList<>();
        TransactionDTO t1 = new TransactionDTO();
        t1.setFirstName(CREDIT);
        t1.setDescription(BANKINGTRANSFER);
        t1.setAmount(new BigDecimal("50").setScale(2));
        dtos.add(t1);

        TransactionDTO t2 = new TransactionDTO();
        t2.setFirstName(DEBIT);
        t2.setDescription(BANKINGTRANSFER);
        t2.setAmount(new BigDecimal("-10").setScale(2));
        dtos.add(t2);

        TransactionDTO t3 = new TransactionDTO();
        t3.setFirstName(user1.getFirstName());
        t3.setDescription("Restau");
        t3.setAmount(new BigDecimal("-5").setScale(2));
        dtos.add(t3);

        TransactionDTO t4 = new TransactionDTO();
        t4.setFirstName(user1.getFirstName());
        t4.setDescription("LaserGame");
        t4.setAmount(new BigDecimal("4").setScale(2));
        dtos.add(t4);

        return dtos;
    }

}
