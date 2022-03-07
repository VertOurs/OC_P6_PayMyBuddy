package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.dto.*;
import fr.vertours.buddtwo.model.BankAccount;
import fr.vertours.buddtwo.model.Role;
import fr.vertours.buddtwo.model.User;
import fr.vertours.buddtwo.repository.UserRepository;
import fr.vertours.buddtwo.security.MyUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;


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

    private User returnGeorgeUser() {
        User george = new User();
        george.setId(1L);
        george.setFirstName("George");
        george.setLastName("Bidon");
        george.setEmail("george@gmail.com");
        george.setPassword("georgeTest");
        return george;
    }
    private User returnKateUser() {
        User kate = new User();
        kate.setId(2L);
        kate.setFirstName("Kate");
        kate.setLastName("Fake");
        kate.setEmail("kate@gmail.com");
        return kate;
    }

    private User returnAdminUserInDB() {
        User admin = new User();
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setEmail("admin@mail.com");
        admin.setPassword("admin");
        return admin;
    }
    private RegistrationDTO returnRegDTO() {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setFirstName("Harry");
        dto.setLastName("False");
        dto.setEmail("harry@gmail.com");
        dto.setPassword("test");
        dto.setConfirmation("test");
        return dto;
    }
    private MyUserDetails getMyUserDetailsOfGeorge() {
        User george = returnGeorgeUser();
        george.setRoleList(new ArrayList<>());
        george.getRoleList().add(new Role("USER"));
        return new MyUserDetails(
                george.getRoleList(), george.getPassword(),
                george.getEmail(), george);
    }

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
    void delFriendByEmail() {
        User george = returnGeorgeUser();
        User kate = returnKateUser();
        when(userRepository.findByEmail(george.getEmail()))
                .thenReturn(george);
        when(userRepository.findByEmail(kate.getEmail()))
                .thenReturn(kate);

        classUnderTest.delFriendByEmail(george.getEmail(), kate.getEmail());

        verify(userRepository, times(2))
                .findByEmail(any(String.class));
        verify(userRepository, times(1))
                .save(george);
        verify(userRepository, times(0))
                .save(kate);
    }

    @Test //TODO tester le cas d'exception
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

    @Test //TODO gestion erreurs ?
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