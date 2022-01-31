package fr.vertours.buddtwo.controller;

import fr.vertours.buddtwo.configuration.MyUserDetails;
import fr.vertours.buddtwo.model.BankAccount;
import fr.vertours.buddtwo.model.User;
import fr.vertours.buddtwo.repository.BankAccountRepository;
import fr.vertours.buddtwo.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private UserRepository userRepository;
    private BankAccountRepository bankRepository;

    public HomeController(UserRepository userRepository, BankAccountRepository bankRepository) {
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
    }

    @GetMapping("/home")
    public String getHomePage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        User user = userRepository.findByEmail(myUserDetails.getUsername());

        model.addAttribute("user", user.getFirstName());
        model.addAttribute("balance", user.getBuddyBalance());

        model.addAttribute("bank", new BankAccount());
        return "home";
    }


    @PostMapping("/createBank")
    public String postBankAccount(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        User user = userRepository.findByEmail(myUserDetails.getUsername());

        BankAccount bankAccount = new BankAccount();

        bankAccount.setUser(user);
        bankAccount.setBic("bic");
        bankAccount.setIban("iban");
        bankRepository.save(bankAccount);
        user.setBankAccount(bankAccount);
        userRepository.save(user);

        model.addAttribute("user", user.getFirstName());
        model.addAttribute("balance", user.getBuddyBalance());
        model.addAttribute("bank", bankAccount);
        return "home";

    }

    @GetMapping("/secure")
    public String getSecurePage() {
        return "secure";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }


    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
}
