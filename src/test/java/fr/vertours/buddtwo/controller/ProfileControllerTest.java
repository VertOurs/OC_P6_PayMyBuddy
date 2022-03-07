package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.dto.ChangePasswordDTO;
import fr.vertours.buddtwo.dto.RegistrationDTO;
import fr.vertours.buddtwo.exception.PasswordDoesNotMatchException;
import fr.vertours.buddtwo.security.MyUserDetails;
import fr.vertours.buddtwo.service.ProfileUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import static fr.vertours.buddtwo.UtilUnitTestMethods.getMyUserDetailsOfJean;
import static fr.vertours.buddtwo.UtilUnitTestMethods.returnRegDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProfileControllerTest {

    private final ProfileUserService userService =
            mock(ProfileUserService.class);
    private final BindingResult bindingResult = mock(BindingResult.class);

    private final ProfileController classUnderTest =
            new ProfileController(userService);

    @Test
    void showProfilePage() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        ModelAndView mvActual = classUnderTest.showProfilePage(mud);
        assertEquals("profile", mvActual.getViewName());

    }

    @Test
    void showAddBankPage() {
        ModelAndView mvActual = classUnderTest.showAddBankPage();
        assertEquals("changePassword", mvActual.getViewName());
    }

    @Test
    void submitAddBankForm() {
        ChangePasswordDTO dto = new ChangePasswordDTO();
        MyUserDetails mud = getMyUserDetailsOfJean();
        doNothing().when(userService).updatePassword(dto, mud);
        when(bindingResult.hasErrors()).thenReturn(false);

        classUnderTest.submitAddBankForm(dto, bindingResult, mud);

        verify(userService, times(1))
                .updatePassword(dto, mud);
    }

    @Test
    void submitAddBankFormBindingErrors() {
        ChangePasswordDTO dto = new ChangePasswordDTO();
        MyUserDetails mud = getMyUserDetailsOfJean();
        doNothing().when(userService).updatePassword(dto, mud);
        when(bindingResult.hasErrors()).thenReturn(true);

        ModelAndView mvActual =
                classUnderTest.submitAddBankForm(dto, bindingResult, mud);

        assertEquals("changePassword", mvActual.getViewName());
    }

    @Test
    void submitAddBankFormPasswordErrors() {
        ChangePasswordDTO dto = new ChangePasswordDTO();
        MyUserDetails mud = getMyUserDetailsOfJean();
        doThrow(PasswordDoesNotMatchException.class)
                .when(userService).updatePassword(dto, mud);
        when(bindingResult.hasErrors()).thenReturn(false);

        ModelAndView mvActual =
                classUnderTest.submitAddBankForm(dto, bindingResult, mud);

        assertEquals("changePassword", mvActual.getViewName());
    }
}
