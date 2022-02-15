package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.configuration.MyUserDetails;
import fr.vertours.buddtwo.dto.AddBankDTO;
import fr.vertours.buddtwo.dto.ChangePasswordDTO;
import fr.vertours.buddtwo.dto.HomeDTO;
import fr.vertours.buddtwo.dto.ProfileDTO;
import fr.vertours.buddtwo.exception.EmailAlreadyPresentException;
import fr.vertours.buddtwo.exception.PasswordDoesNotMatchException;
import fr.vertours.buddtwo.service.ProfileUserService;
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
public class ProfileController {

    private final ProfileUserService userService;

    public ProfileController(ProfileUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ModelAndView showProfilePage(@AuthenticationPrincipal MyUserDetails myUD) {
        ProfileDTO profileDTO =  userService.findProfileDTO(myUD);
        ModelAndView mv = new ModelAndView("profile");
        mv.addObject("dto", profileDTO);
        return mv;
    }

    @GetMapping("/changePassword")
    public ModelAndView showAddBankPage() {
        ModelAndView mv = new ModelAndView("changePassword");
        mv.addObject("dto", new ChangePasswordDTO());
        return mv;
    }

    @PostMapping("/changePassword")
    public ModelAndView submitAddBankForm(@Valid @ModelAttribute("dto") ChangePasswordDTO changePasswordDTO,BindingResult bindingResult, @AuthenticationPrincipal MyUserDetails myUD) {
        if(bindingResult.hasErrors()){
            return new ModelAndView("changePassword");
        }
        try {
        userService.updatePassword(changePasswordDTO, myUD);
        } catch (PasswordDoesNotMatchException e) {
            bindingResult.rejectValue("","",e.getMessage());
            return new ModelAndView("changePassword");
        }
        return new ModelAndView(new RedirectView("/logout"));
    }
}

