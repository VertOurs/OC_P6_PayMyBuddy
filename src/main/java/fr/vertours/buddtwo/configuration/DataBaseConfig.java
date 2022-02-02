package fr.vertours.buddtwo.configuration;

import fr.vertours.buddtwo.model.User;
import fr.vertours.buddtwo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataBaseConfig {

    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;

    public DataBaseConfig(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner demo() {
        return args -> {
            User arthur = new User("Arthur", "Pons", "ap@mail.com", "nope");
            userService.saveUserByUser(arthur);
            User angelina = new User("Angelina", "Dupond", "ad@mail.com",  "biz");
            userService.saveUserByUser(angelina);
        };
    }
}
