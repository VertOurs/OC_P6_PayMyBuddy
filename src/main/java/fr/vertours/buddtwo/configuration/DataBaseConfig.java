package fr.vertours.buddtwo.configuration;

import fr.vertours.buddtwo.service.RoleServiceImpl;
import fr.vertours.buddtwo.service.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataBaseConfig {

    private UserServiceImpl userServiceImpl;
    private RoleServiceImpl roleService;

    public DataBaseConfig(UserServiceImpl userServiceImpl, RoleServiceImpl roleService) {
        this.userServiceImpl = userServiceImpl;
        this.roleService = roleService;
    }

    @Bean
    public CommandLineRunner demo() {
        return args -> {
//            roleService.save(new Role("User"));
//
//            User arthur = new User("Arthur", "Pons", "ap@mail.com", "nope");
//            userServiceImpl.saveUserByUser(arthur);
//            User angelina = new User("Angelina", "Dupond", "ad@mail.com",  "biz");
//            userServiceImpl.saveUserByUser(angelina);
//            User bob = new User("Bob", "Petronel", "bp@mail.com","aze");
//            userServiceImpl.saveUserByUser(bob);
//            User benoit = new User("Benoit", "bierrard", "bb@mail.com", "zut");
//            userServiceImpl.saveUserByUser(benoit);
//            User melanie = new User("Mélanie", "Jordy", "mj@mail.com", "mel");
//            userServiceImpl.saveUserByUser(melanie);

//            userServiceImpl.addFriendInFriendList(arthur, angelina);
//            userServiceImpl.addFriendInFriendList(arthur, benoit);
//            userServiceImpl.addFriendInFriendList(arthur, melanie);
//            userServiceImpl.addFriendInFriendList(melanie, angelina);
//
//            userServiceImpl.testdestrucs(arthur);
//            userServiceImpl.testdestrucs(angelina);
//            userServiceImpl.testdestrucs(melanie);

        };
    }
}
