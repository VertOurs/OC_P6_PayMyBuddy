package fr.vertours.buddtwo.configuration;

import fr.vertours.buddtwo.model.User;
import fr.vertours.buddtwo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class MyUserDetailsService implements UserDetailsService {


    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(email);
        UserDetails userDetails = new MyUserDetails(user.getRoleList(), user.getPassword(), user.getEmail(), user);
        return userDetails;
    }
}
