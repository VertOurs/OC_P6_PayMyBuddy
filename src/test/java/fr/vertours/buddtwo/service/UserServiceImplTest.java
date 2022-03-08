package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.dto.*;
import fr.vertours.buddtwo.exception.EmailAlreadyPresentException;
import fr.vertours.buddtwo.exception.EmailNotPresentInApplicationException;
import fr.vertours.buddtwo.exception.EmailNotPresentInFriendsException;
import fr.vertours.buddtwo.exception.PasswordDoesNotMatchException;
import fr.vertours.buddtwo.model.BankAccount;
import fr.vertours.buddtwo.model.Role;
import fr.vertours.buddtwo.model.User;
import fr.vertours.buddtwo.repository.UserRepository;
import fr.vertours.buddtwo.security.MyUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;


import static fr.vertours.buddtwo.UtilUnitTestMethods.*;
import static fr.vertours.buddtwo.dto.FriendDTO.getFriendDTOByUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    UserRepository userRepository = mock(UserRepository.class);
    BCryptPasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);
    RoleServiceImpl roleService = mock(RoleServiceImpl.class);


    UserServiceImpl classUnderTest = new UserServiceImpl(
            userRepository, passwordEncoder, roleService);


    @Test
    void findByEmail() {
        User george = returnGeorgeUser();
        when(userRepository.findByEmail(george.getEmail()))
                .thenReturn(george);

        User user = classUnderTest.findByEmail(george.getEmail());

        verify(userRepository, times(1))
                .findByEmail(george.getEmail());
        assertEquals(returnGeorgeUser().getBuddyBalance().toString(),
                "0.00");
        assertEquals(returnGeorgeUser().getEmail(), user.getEmail());
        assertEquals(returnGeorgeUser().getId(), user.getId());
    }

    @Test
    void initAdminUserWithoutUserInDB() {
        User admin = returnAdminUserInDB();
        when(userRepository.findByEmail(admin.getEmail()))
                .thenReturn(null);

        classUnderTest.initAdminUser();

        verify(roleService, times(1)).findAdminRole();
        verify(passwordEncoder, times(1))
                .encode(admin.getPassword());
        verify(userRepository, times(1))
                .findByEmail(admin.getEmail());

        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    void initAdminUserWithUserInDB() {
        User admin = returnAdminUserInDB();
        when(userRepository.findByEmail(admin.getEmail()))
                .thenReturn(admin);

        classUnderTest.initAdminUser();

        verify(userRepository, times(0))
                .save(any(User.class));
    }

    @Test
    void updateUser() {
        User user = returnGeorgeUser();
        when(userRepository.save(user))
                .thenReturn(user);

        classUnderTest.updateUser(user);

        verify(userRepository, times(1))
                .save(user);
    }

    @Test
    void addFriendByEmail() {
        User george = returnGeorgeUser();
        User kate = returnKateUser();
        when(userRepository.findByEmail(george.getEmail()))
                .thenReturn(george);
        when(userRepository.findByEmail(kate.getEmail()))
                .thenReturn(kate);

        classUnderTest.addFriendByEmail(george.getEmail(), kate.getEmail());

        verify(userRepository, times(2))
                .findByEmail(any(String.class));
        verify(userRepository, times(1))
                .save(george);
        verify(userRepository, times(0))
                .save(kate);
    }
    @Test
    void addFriendByEmailException() {
        User george = returnGeorgeUser();
        User kate = returnKateUser();
        when(userRepository.findByEmail(george.getEmail()))
                .thenReturn(george);
        when(userRepository.findByEmail(kate.getEmail()))
                .thenReturn(null);
        Exception exception =
                assertThrows(EmailNotPresentInApplicationException.class,
                        () -> classUnderTest.addFriendByEmail(
                                george.getEmail(), kate.getEmail()));
        assertTrue(exception.getMessage()
                .contains("No one is registered with the email  :"));

    }

    @Test
    void delFriendByEmail() {
        User jean = returnJeanUser();
        User george = returnGeorgeUser();

        when(userRepository.findByEmail(jean.getEmail()))
                .thenReturn(jean);
        when(userRepository.findByEmail(george.getEmail()))
                .thenReturn(george);

        classUnderTest.delFriendByEmail(jean.getEmail(), george.getEmail());

        verify(userRepository, times(2))
                .findByEmail(any(String.class));
        verify(userRepository, times(1))
                .save(jean);
        verify(userRepository, times(0))
                .save(george);
    }

    @Test
    void delFriendByEmailException() {
        User kate = returnKateUser();
        User george = returnGeorgeUser();
        when(userRepository.findByEmail(kate.getEmail()))
                .thenReturn(kate);
        when(userRepository.findByEmail(george.getEmail()))
                .thenReturn(george);
        Exception exception =
                assertThrows(EmailNotPresentInFriendsException.class,
                        () -> classUnderTest.delFriendByEmail(
                                kate.getEmail(), george.getEmail()));

        assertTrue(exception.getMessage()
                .contains("No friend uses this email :"));


    }

    @Test
    void saveUserByRegistrationDTO() {
        RegistrationDTO dto = returnRegDTO();
        classUnderTest.saveUserByRegistrationDTO(dto);

        verify(roleService, times(1)).findUSERRole();
        verify(passwordEncoder, times(1))
                .encode(any(String.class));
        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    void saveUserByRegistrationDTOException() {
        RegistrationDTO dto = returnRegDTO();
        User kate = returnKateUser();
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(kate);
        Exception exception =
                assertThrows(EmailAlreadyPresentException.class,
                        () -> classUnderTest.saveUserByRegistrationDTO(dto));

        assertTrue(exception.getMessage()
                .contains("was already use in application"));


    }

    @Test
    void findHomeDTOByMyUserDetails() {
        User george = returnGeorgeUser();
        george.setBankAccount(new BankAccount(george, "iban",
                "bic", "bankName"));
        MyUserDetails mud = getMyUserDetailsOfGeorge();
        when(userRepository.findByEmail(mud.getUsername()))
                .thenReturn(george);

        HomeDTO actualDto = new HomeDTO(
                george.getFirstName(), george.getLastName(),
                String.valueOf(george.getBuddyBalance()),
                george.getBankAccount().getCustomizeName());

        HomeDTO dtoExpected = classUnderTest.findHomeDTOByMyUserDetails(mud);

        verify(userRepository, times(1))
                .findByEmail(mud.getUsername());
        assertEquals( actualDto.getFirstName(), dtoExpected.getFirstName());
        assertEquals(actualDto.getBankName(), dtoExpected.getBankName());

    }

    @Test
    void findProfileDTO() {
        MyUserDetails mud = getMyUserDetailsOfGeorge();
        ProfileDTO actualDto = new ProfileDTO();
        actualDto.setFirstName(mud.getUser().getFirstName());
        actualDto.setLastName(mud.getUser().getLastName());
        actualDto.setEmail(mud.getUser().getEmail());

        ProfileDTO expectedDto = classUnderTest.findProfileDTO(mud);

        assertEquals(actualDto.getEmail(), expectedDto.getEmail());
    }

    @Test
    void updatePassword() {
        User george = returnGeorgeUser();
        MyUserDetails mud = getMyUserDetailsOfGeorge();
        when(userRepository.save(george)).thenReturn(george);
        ChangePasswordDTO dto = new ChangePasswordDTO();
        dto.setNewPassword("newPassword");
        dto.setNewPasswordConfirmation("newPassword");
        dto.setOldPassword("georgeTest");

        classUnderTest.updatePassword(dto, mud);

        verify(userRepository, times(1))
                .save(any(User.class));
    }
    @Test
    void updatePasswordException() {
        User george = returnGeorgeUser();
        MyUserDetails mud = getMyUserDetailsOfGeorge();
        when(userRepository.save(george)).thenReturn(george);
        ChangePasswordDTO dto = new ChangePasswordDTO();
        dto.setNewPassword("newPassword");
        dto.setNewPasswordConfirmation("newPassword");
        dto.setOldPassword("er");

        Exception exception =
                assertThrows(PasswordDoesNotMatchException.class,
                        () -> classUnderTest.updatePassword(dto, mud));


        assertTrue(exception.getMessage()
                .contains("please enter your current password"));

    }

    @Test
    void findContactDTO() {
        User george = returnGeorgeUser();
        User kate = returnKateUser();
        george.getMyFriendList().add(kate);
        MyUserDetails mud = getMyUserDetailsOfGeorge();
        when(userRepository.findByEmail(mud.getUsername()))
                .thenReturn(george);
        ContactDTO actualDto = new ContactDTO();
        actualDto.getFriendDTOS().add(getFriendDTOByUser(kate));

        ContactDTO expectedDto = classUnderTest.findContactDTO(mud);

        verify(userRepository, times(1))
                .findByEmail(mud.getUsername());
        assertEquals(expectedDto.getFriendDTOS().get(0).getEmail(),
                actualDto.getFriendDTOS().get(0).getEmail());


    }
}