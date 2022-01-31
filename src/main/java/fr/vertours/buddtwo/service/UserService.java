package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.model.User;
import fr.vertours.buddtwo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(String firstName, String lastName, String email, String password) {
        User user = new User(firstName, lastName, email, password);
        userRepository.save(user);
    }
}
