package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.configuration.MyUserDetails;
import fr.vertours.buddtwo.dto.ChangePasswordDTO;
import fr.vertours.buddtwo.dto.HomeDTO;
import fr.vertours.buddtwo.dto.ProfileDTO;
import fr.vertours.buddtwo.dto.RegistrationDTO;
import fr.vertours.buddtwo.exception.EmailAlreadyPresentException;
import fr.vertours.buddtwo.exception.PasswordDoesNotMatchException;
import fr.vertours.buddtwo.model.User;
import fr.vertours.buddtwo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements RegistrationService, HomeUserService, ProfileUserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Use only by DataBaseConfig
     */
    public void saveUserByUser(User user) {
        User newUser = new User(user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);
    }

    public void addFriendInFriendList(User user, User friend) {
        User userDB = userRepository.findByEmail(user.getEmail());
        User friendDB = userRepository.findByEmail(friend.getEmail());
        friendDB.getMyFriendList().add(userDB);
        userRepository.save(userDB);

    }

    public void testdestrucs(User user){
        User userDB = userRepository.findByEmail(user.getEmail());
        //System.out.println(userDB.getMyFriendList().size());
        for(User u : userDB.getMyFriendList()) {
            System.out.println(userRepository.findByEmail(u.getEmail()));
        }
    }
    public void saveUserByRegistrationDTO(RegistrationDTO regDTO) {
        isEmailAlreadyExistInDataBase(regDTO.getEmail());
        User user = new User(regDTO.getFirstName(),
                regDTO.getLastName(),
                regDTO.getEmail(),
                passwordEncoder.encode(regDTO.getPassword()));
        userRepository.save(user);
    }

    private void isEmailAlreadyExistInDataBase(String email)  {
        Optional<User> isUserExist = Optional.ofNullable(userRepository.findByEmail(email));
        if(isUserExist.isPresent()) {
            throw new EmailAlreadyPresentException(email);
        }
    }

    public HomeDTO findHomeDTOByMyUserDetails(MyUserDetails myUD) {
        User user = userRepository.findByEmail(myUD.getUsername());
        HomeDTO dto = new HomeDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setBalance(String.valueOf(user.getBuddyBalance()));
        if(user.getBankAccount()!=null) {
            dto.setBankName(user.getBankAccount().getCustomizeName());
        }
        return dto;
    }

    @Override
    public ProfileDTO findProfileDTO(MyUserDetails myUD) {
        ProfileDTO dto = new ProfileDTO();
        dto.setFirstName(myUD.getUser().getFirstName());
        dto.setLastName(myUD.getUser().getLastName());
        dto.setEmail(myUD.getUser().getEmail());

        return dto;
    }

    @Override
    public void updatePassword(ChangePasswordDTO dto, MyUserDetails myUserDetails) {
        isPasswordsMatch(dto, myUserDetails);
        User user = myUserDetails.getUser();
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    private void isPasswordsMatch(ChangePasswordDTO dto, MyUserDetails myUserDetails) {
        if(!dto.getOldPassword().equals(myUserDetails.getPassword())) {
            throw new PasswordDoesNotMatchException();
        }
    }
}
