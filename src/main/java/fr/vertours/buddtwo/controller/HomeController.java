package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.configuration.MyUserDetails;
import fr.vertours.buddtwo.dto.AddBankDTO;
import fr.vertours.buddtwo.dto.HomeDTO;
import fr.vertours.buddtwo.dto.RegistrationDTO;
import fr.vertours.buddtwo.exception.EmailAlreadyPresentException;
import fr.vertours.buddtwo.service.UserService;
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

    UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public ModelAndView showHomePage(@AuthenticationPrincipal MyUserDetails myUD) {
        HomeDTO homeDTO = userService.findHomeDTOByMyUserDetails(myUD);

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
    public ModelAndView submitAddBankForm(@Valid
                                              @ModelAttribute("addBankDTO")
                                                      AddBankDTO addBankDTO,
                                          @AuthenticationPrincipal
                                                  MyUserDetails myUD,
                                          BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ModelAndView("addBank");
        }
            userService.saveNewBankAccount(addBankDTO, myUD);

        return new ModelAndView(new RedirectView("/home"));
    }
}
