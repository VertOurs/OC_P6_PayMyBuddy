package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.exception.NoEnoughMoneyException;
import fr.vertours.buddtwo.security.MyUserDetails;
import fr.vertours.buddtwo.dto.AddBankDTO;
import fr.vertours.buddtwo.dto.BankingTransferDTO;
import fr.vertours.buddtwo.dto.HomeDTO;
import fr.vertours.buddtwo.service.HomeBankService;
import fr.vertours.buddtwo.service.HomeTransferService;
import fr.vertours.buddtwo.service.HomeUserService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class HomeController {

    final HomeUserService userService;
    final HomeBankService bankService;
    final HomeTransferService transferService;

    public HomeController(HomeUserService userService,
                          HomeBankService bankService,
                          HomeTransferService transferService) {
        this.userService = userService;
        this.bankService = bankService;
        this.transferService = transferService;
    }

    @GetMapping("/home")
    public ModelAndView showHomePage(
            @AuthenticationPrincipal MyUserDetails myUD) {
        HomeDTO homeDTO =  userService.findHomeDTOByMyUserDetails(myUD);
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("dto", homeDTO);
        return mv;
    }

    @GetMapping("/addBank")
    public ModelAndView showAddBankPage() {
        ModelAndView mv = new ModelAndView("addBank");
        mv.addObject("addBankDTO", new AddBankDTO());
        return mv;
    }

    @PostMapping("/addBank")
    public ModelAndView submitAddBankForm(
            @Valid @ModelAttribute("addBankDTO") AddBankDTO addBankDTO,
            BindingResult bindingResult,
            @AuthenticationPrincipal MyUserDetails myUD) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("addBank");
        }
            bankService.saveBankAccount(addBankDTO, myUD);

        return new ModelAndView(new RedirectView("/home"));
    }

    @GetMapping("/creditApplication")
    public ModelAndView showCreditApplicationForm() {
        ModelAndView mv = new ModelAndView("creditApplication");
        mv.addObject("dto", new BankingTransferDTO());
        return mv;
    }

    @PostMapping("/creditApplication")
    public ModelAndView submitCreditApplicationForm(
            @Valid @ModelAttribute("dto") BankingTransferDTO dto,
            BindingResult bindingResult,
            @AuthenticationPrincipal MyUserDetails myUD) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("creditApplication");
        }

        transferService.creditApplicationAccount(dto, myUD);

        return new ModelAndView(new RedirectView("/home"));
    }

    @GetMapping("/debitApplication")
    public ModelAndView showDebitApplicationForm() {
        ModelAndView mv = new ModelAndView("debitApplication");
        mv.addObject("dto", new BankingTransferDTO());
        return mv;
    }

    @PostMapping("/debitApplication")
    public ModelAndView submitDebitApplicationForm(
            @Valid @ModelAttribute("dto") BankingTransferDTO dto,
            final BindingResult bindingResult,
            @AuthenticationPrincipal MyUserDetails myUD) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("debitApplication");
        } try {
        transferService.debitApplicationAccount(dto, myUD);
        } catch (NoEnoughMoneyException e) {
            bindingResult.rejectValue(
                    "amount", "", e.getMessage());
            return new ModelAndView("debitApplication");
        }
        return new ModelAndView(new RedirectView("/home"));
    }
}
