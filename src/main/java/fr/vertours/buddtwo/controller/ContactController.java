package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.configuration.MyUserDetails;
import fr.vertours.buddtwo.dto.ProfileDTO;
import fr.vertours.buddtwo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/contact")
    public ModelAndView showProfilePage(@AuthenticationPrincipal MyUserDetails myUD) {
        ProfileDTO profileDTO =  userService.findProfileDTO(myUD);
        ModelAndView mv = new ModelAndView("contact");
        mv.addObject("dto", profileDTO);
        return mv;
    }
}
