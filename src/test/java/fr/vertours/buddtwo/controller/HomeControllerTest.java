package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.dto.AddBankDTO;
import fr.vertours.buddtwo.dto.AddFriendDTO;
import fr.vertours.buddtwo.dto.BankingTransferDTO;
import fr.vertours.buddtwo.security.MyUserDetails;
import fr.vertours.buddtwo.service.HomeBankService;
import fr.vertours.buddtwo.service.HomeTransferService;
import fr.vertours.buddtwo.service.HomeUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import static fr.vertours.buddtwo.UtilUnitTestMethods.getMyUserDetailsOfJean;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class HomeControllerTest {

    final HomeUserService userService = mock(HomeUserService.class);
    final HomeBankService bankService = mock(HomeBankService.class);
    final HomeTransferService transferService = mock(HomeTransferService.class);
    private final BindingResult bindingResult = mock(BindingResult.class);

    private final HomeController classUnderTest =
            new HomeController(userService, bankService, transferService);

    @Test
    void showHomePage() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        ModelAndView mvActual = classUnderTest.showHomePage(mud);
        assertEquals("home", mvActual.getViewName());
    }

    @Test
    void showAddBankPage() {
        ModelAndView mvActual = classUnderTest.showAddBankPage();
        assertEquals("addBank", mvActual.getViewName());
    }

    @Test
    void submitAddBankForm() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        AddBankDTO dto = new AddBankDTO();
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing()
                .when(bankService)
                .saveBankAccount(dto, mud);

        classUnderTest.submitAddBankForm(dto, bindingResult,mud);

        verify(bankService, times(1))
                .saveBankAccount(dto, mud);
    }
    @Test
    void submitAddBankFormBindingErrors() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        AddBankDTO dto = new AddBankDTO();
        when(bindingResult.hasErrors()).thenReturn(true);
        doNothing()
                .when(bankService)
                .saveBankAccount(dto, mud);

        ModelAndView mvActual =
                classUnderTest.submitAddBankForm(dto, bindingResult,mud);

        assertEquals("addBank", mvActual.getViewName());

    }

    @Test
    void showCreditApplicationForm() {
        ModelAndView mvActual = classUnderTest.showCreditApplicationForm();
        assertEquals("creditApplication", mvActual.getViewName());
    }

    @Test
    void submitCreditApplicationForm() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        BankingTransferDTO dto = new BankingTransferDTO();
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing()
                .when(transferService)
                .creditApplicationAccount(dto, mud);

        classUnderTest.submitCreditApplicationForm(dto, bindingResult,mud);

        verify(transferService, times(1))
                .creditApplicationAccount(dto, mud);
    }

    @Test
    void submitCreditApplicationFormBindingErrors() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        BankingTransferDTO dto = new BankingTransferDTO();
        when(bindingResult.hasErrors()).thenReturn(true);
        doNothing()
                .when(transferService)
                .creditApplicationAccount(dto, mud);

        ModelAndView mvActual =
                classUnderTest.submitCreditApplicationForm(dto, bindingResult,mud);

        assertEquals("creditApplication", mvActual.getViewName());
    }

    @Test
    void showDebitApplicationForm() {
        ModelAndView mvActual = classUnderTest.showDebitApplicationForm();
        assertEquals("debitApplication", mvActual.getViewName());
    }

    @Test
    void submitDebitApplicationForm() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        BankingTransferDTO dto = new BankingTransferDTO();
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing()
                .when(transferService)
                .debitApplicationAccount(dto, mud);

        classUnderTest.submitDebitApplicationForm(dto, bindingResult,mud);

        verify(transferService, times(1))
                .debitApplicationAccount(dto, mud);
    }
    @Test
    void submitdebitApplicationFormBindingErrors() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        BankingTransferDTO dto = new BankingTransferDTO();
        when(bindingResult.hasErrors()).thenReturn(true);
        doNothing()
                .when(transferService)
                .debitApplicationAccount(dto, mud);

        ModelAndView mvActual =
                classUnderTest.submitDebitApplicationForm(dto, bindingResult,mud);

        assertEquals("debitApplication", mvActual.getViewName());
    }
}