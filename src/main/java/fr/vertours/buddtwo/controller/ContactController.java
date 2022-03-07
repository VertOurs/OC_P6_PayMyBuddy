package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.security.MyUserDetails;
import fr.vertours.buddtwo.dto.AddFriendDTO;
import fr.vertours.buddtwo.dto.ContactDTO;
import fr.vertours.buddtwo.service.ContactUserService;
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
public class ContactController {

    private final ContactUserService userService;

    public ContactController(final ContactUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/contact")
    public ModelAndView showProfilePage(
            @AuthenticationPrincipal final MyUserDetails myUD) {
        ContactDTO contactDTO =  userService.findContactDTO(myUD);
        ModelAndView mv = new ModelAndView("contact");
        mv.addObject("dto", contactDTO);
        return mv;
    }

    @GetMapping("/addFriend")
    public ModelAndView showAddFriendForm() {
        ModelAndView mv = new ModelAndView("addFriend");
        mv.addObject("dto", new AddFriendDTO());
        return mv;

    }

    @PostMapping("/addFriend")
    public ModelAndView submitAddFriendForm(
            @Valid @ModelAttribute("dto") final AddFriendDTO addFriendDTO,
            final BindingResult bindingResult,
            final @AuthenticationPrincipal MyUserDetails myUD) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("addFriend");
        }
        userService.addFriendByEmail(myUD.getUsername(),
                addFriendDTO.getFriendEmail());
        return new ModelAndView(new RedirectView("/contact"));
    }

    @GetMapping("/delFriend")
    public ModelAndView showDelFriendForm() {
        ModelAndView mv = new ModelAndView("delFriend");
        mv.addObject("dto", new AddFriendDTO());
        return mv;

    }

    @PostMapping("/delFriend")
    public ModelAndView submitDelFriendForm(
            @Valid @ModelAttribute("dto") final AddFriendDTO addFriendDTO,
            final BindingResult bindingResult,
            @AuthenticationPrincipal final MyUserDetails myUD) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("delFriend");
        }
        userService.delFriendByEmail(
                myUD.getUsername(), addFriendDTO.getFriendEmail());
        return new ModelAndView(new RedirectView("/contact"));
    }
}
