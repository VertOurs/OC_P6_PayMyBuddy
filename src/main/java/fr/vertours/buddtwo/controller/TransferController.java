package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.configuration.MyUserDetails;
import fr.vertours.buddtwo.dto.TransferDTO;
import fr.vertours.buddtwo.service.TransferService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TransferController {

    private TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("/transfer")
    public ModelAndView showTransferPage(@AuthenticationPrincipal MyUserDetails myUD, @RequestParam("page") Optional<Integer> page) {
        ModelAndView mv = new ModelAndView("transfer");
        int currentPage = page.orElse(1);
        int pageSize = 3;

        TransferDTO transferDTO =  transferService.findTransferDTO(myUD, PageRequest.of(currentPage-1, pageSize));
        mv.addObject("dto", transferDTO);

        int totalPages = transferDTO.getTransactionDTOS().getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            mv.addObject("pageNumbers", pageNumbers);
        }
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
