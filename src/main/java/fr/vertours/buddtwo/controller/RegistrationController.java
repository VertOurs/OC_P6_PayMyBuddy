package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.dto.RegistrationDTO;
import fr.vertours.buddtwo.exception.EmailAlreadyPresentException;
import fr.vertours.buddtwo.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final RegistrationService service;

    public RegistrationController(RegistrationService service) {
        this.service = service;
    }

    @GetMapping("/registrationSuccess")
    public ModelAndView showSuccessPage() {
        return new ModelAndView("registrationSuccess");
    }

    @GetMapping("/registration")
    public ModelAndView showRegistrationPage() {
        ModelAndView mv = new ModelAndView("registration");
        mv.addObject("regDTO", new RegistrationDTO());
        return mv;
    }

    @PostMapping("/registration")
    public ModelAndView submitRegistrationForm(
            @Valid @ModelAttribute("regDTO") RegistrationDTO regDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("registration");
        }
        try {
            service.saveUserByRegistrationDTO(regDTO);
        } catch (EmailAlreadyPresentException e) {
            bindingResult.rejectValue("email", "", e.getMessage());
            return new ModelAndView("registration");
        }

        return new ModelAndView(new RedirectView("/registrationSuccess"));
    }
}
