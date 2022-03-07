package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.service.AuthTransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
class AuthControllerTest {



    private final AuthTransferService transferService =
            mock(AuthTransferService.class);

    private final AuthController classUnderTest =
            new AuthController(transferService);


    @Test
    void showLoginPage() {
        ModelAndView mvActual = classUnderTest.showLoginPage();
        assertEquals("login", mvActual.getViewName());
    }

    @Test
    void showLoginPageFromRegistration() {
        ModelAndView mvActual = classUnderTest.showLoginPageFromRegistration();
        assertEquals("login", mvActual.getViewName());
    }

    @Test
    void showLogoffPage() {
        ModelAndView mvActual = classUnderTest.showLogoffPage();
        assertEquals("logoff", mvActual.getViewName());
    }

    @Test
    void showAdminPage() {
        ModelAndView mvActual = classUnderTest.showAdminPage();
        assertEquals("admin", mvActual.getViewName());
    }
}