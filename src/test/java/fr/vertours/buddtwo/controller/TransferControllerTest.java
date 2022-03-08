package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.dto.TransactionDTO;
import fr.vertours.buddtwo.dto.TransferDTO;
import fr.vertours.buddtwo.exception.NoEnoughMoneyException;
import fr.vertours.buddtwo.security.MyUserDetails;
import fr.vertours.buddtwo.service.TransferService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static fr.vertours.buddtwo.UtilUnitTestMethods.getMyUserDetailsOfJean;
import static fr.vertours.buddtwo.UtilUnitTestMethods.returnGetTransferDTOTransactionsList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class TransferControllerTest {

    private final TransferService transferService = mock(TransferService.class);
    private final BindingResult bindingResult = mock(BindingResult.class);


    private TransferController classUnderTest =
            new TransferController(transferService);

    @Test
    void showTransferPage() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        TransferDTO dto = returnGetTransferDTOTransactionsList();
        Optional<Integer> page = Optional.of(1);
        int currentPage = page.orElse(1);
        int pageSize = 3;
        when(transferService.findTransferDTO(
                mud, PageRequest.of(currentPage - 1, pageSize)))
                .thenReturn(dto);


        classUnderTest.showTransferPage(mud, page);

        verify(transferService, times(1))
                .findTransferDTO(
                        mud, PageRequest.of(currentPage - 1, pageSize));
    }
    @Test
    void showTransferPagePage0() {
        MyUserDetails mud = getMyUserDetailsOfJean();
        TransferDTO dto = new TransferDTO();
        List<TransactionDTO> dtos = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 2);
        dto.setTransactionDTOS(
                new PageImpl<>(
                       dtos , pageable, dtos.size()));
        System.out.println(dto.getTransactionDTOS().getTotalPages());
        Optional<Integer> page = Optional.of(1);
        int currentPage = page.orElse(1);
        int pageSize = 3;
        when(transferService.findTransferDTO(
                mud, PageRequest.of(currentPage - 1, pageSize)))
                .thenReturn(dto);


        classUnderTest.showTransferPage(mud, page);

        verify(transferService, times(1))
                .findTransferDTO(
                        mud, PageRequest.of(currentPage - 1, pageSize));
    }

    @Test
    void submitTransferForm() {
        TransferDTO dto = new TransferDTO();
        MyUserDetails mud = getMyUserDetailsOfJean();
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(transferService).makeTransfer(dto, mud);

        classUnderTest.submitTransferForm(dto, bindingResult,mud);

        verify(transferService, times(1))
                .makeTransfer(dto, mud);
    }
    @Test
    void submitTransferFormBindingErrors() {
        TransferDTO dto = new TransferDTO();
        MyUserDetails mud = getMyUserDetailsOfJean();
        when(bindingResult.hasErrors()).thenReturn(true);
        doNothing().when(transferService).makeTransfer(dto, mud);

        ModelAndView mvActual =
            classUnderTest.submitTransferForm(dto, bindingResult, mud);

        assertEquals("transferFailed", mvActual.getViewName());
    }
    @Test
    void submitTransferFormErrors() {
        TransferDTO dto = new TransferDTO();
        MyUserDetails mud = getMyUserDetailsOfJean();
        when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(NoEnoughMoneyException.class)
            .when(transferService).makeTransfer(dto, mud);

        ModelAndView mvActual =
                classUnderTest.submitTransferForm(dto, bindingResult, mud);

        assertEquals("transferFailed", mvActual.getViewName());
    }
    @Test
    void showFailedPage() {
        ModelAndView mvActual = classUnderTest.showFailedPage();
        assertEquals("transferFailed",mvActual.getViewName());
    }
}