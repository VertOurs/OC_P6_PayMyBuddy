package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.configuration.MyUserDetails;
import fr.vertours.buddtwo.dto.HomeDTO;
import fr.vertours.buddtwo.dto.RegistrationDTO;
import fr.vertours.buddtwo.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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
        mv.addObject("homeDTO", homeDTO);
        return mv;
    }
}
