package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.dto.RegistrationDTO;
import fr.vertours.buddtwo.exception.EmailAlreadyPresentException;
import fr.vertours.buddtwo.model.User;
import fr.vertours.buddtwo.security.MyUserDetails;
import fr.vertours.buddtwo.security.MyUserDetailsService;
import fr.vertours.buddtwo.service.RegistrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import static fr.vertours.buddtwo.UtilUnitTestMethods.returnJeanUser;
import static fr.vertours.buddtwo.UtilUnitTestMethods.returnRegDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RegistrationControllerTest {

    private final RegistrationService service = mock(RegistrationService.class);
    private final BindingResult bindingResult = mock(BindingResult.class);

    private final RegistrationController classUnderTest =
            new RegistrationController(service);

    @Test
    void showSuccessPage() {
        ModelAndView mvActual = classUnderTest.showSuccessPage();
        assertEquals("registrationSuccess", mvActual.getViewName());
    }

    @Test
    void showRegistrationPage() {
        ModelAndView mvActual = classUnderTest.showRegistrationPage();
        assertEquals("registration", mvActual.getViewName());
    }

    @Test
    void submitRegistrationForm() throws Exception {
        RegistrationDTO dto = returnRegDTO();
        doNothing().when(service).saveUserByRegistrationDTO(dto);
        when(bindingResult.hasErrors()).thenReturn(false);


        classUnderTest.submitRegistrationForm(dto, bindingResult);


        verify(service, times(1))
                .saveUserByRegistrationDTO(dto);
    }
    @Test
    void submitRegistrationFormWithBindingErrors()  {
        RegistrationDTO dto = returnRegDTO();
        doNothing().when(service).saveUserByRegistrationDTO(dto);
        when(bindingResult.hasErrors()).thenReturn(true);

        ModelAndView mvActual =
                classUnderTest.submitRegistrationForm(dto, bindingResult);

        assertEquals("registration", mvActual.getViewName());

    }
    @Test
    void submitRegistrationFormWithEmailErrors()  {
        RegistrationDTO dto = returnRegDTO();
        doThrow(EmailAlreadyPresentException.class).when(service).saveUserByRegistrationDTO(dto);
        when(bindingResult.hasErrors()).thenReturn(false);

        ModelAndView mvActual =
                classUnderTest.submitRegistrationForm(dto, bindingResult);

        assertEquals("registration", mvActual.getViewName());

    }
}