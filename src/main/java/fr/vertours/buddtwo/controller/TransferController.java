package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.configuration.MyUserDetails;
import fr.vertours.buddtwo.dto.TransferDTO;
import fr.vertours.buddtwo.service.TransferService;
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
public class TransferController {

    private TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("/transfer")
    public ModelAndView showTransferPage(@AuthenticationPrincipal MyUserDetails myUD) {
        TransferDTO transferDTO =  transferService.findTransferDTO(myUD);
        ModelAndView mv = new ModelAndView("transfer");
        mv.addObject("dto", transferDTO);
        return mv;
    }

    @PostMapping("/transfer")
    public ModelAndView submitTransferForm(@Valid @ModelAttribute("dto") TransferDTO dto, BindingResult bindingResult, @AuthenticationPrincipal MyUserDetails myUD) {
        if(bindingResult.hasErrors()){
            return new ModelAndView("transfer");
        }
        transferService.makeTransfer(dto, myUD);
        return new ModelAndView(new RedirectView("/transfer"));
    }
}
