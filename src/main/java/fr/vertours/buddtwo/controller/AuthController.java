package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.dto.AdminDTO;
import fr.vertours.buddtwo.service.AuthTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {



    private final AuthTransferService transferService;

    public AuthController(final AuthTransferService transferService) {
        this.transferService = transferService;
    }

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


    @GetMapping("/admin")
    public ModelAndView showAdminPage() {
        AdminDTO dto = transferService.createAdminDTO();
        ModelAndView mv = new ModelAndView("admin");
        mv.addObject("dto", dto);

        return mv;
    }
}
