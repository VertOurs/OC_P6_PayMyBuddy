package fr.vertours.buddtwo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        return new ModelAndView("login");
    }

    @GetMapping("/registration/login")
    public ModelAndView showLoginPageFromRegistration() {
        return new ModelAndView("login");
    }

    @GetMapping("/logoff")
    public ModelAndView showLogoffPage() {
        return new ModelAndView("logoff");
    }

}
