package fr.vertours.buddtwo.service;

import fr.vertours.buddtwo.configuration.MyUserDetails;
import fr.vertours.buddtwo.dto.AddBankDTO;
import fr.vertours.buddtwo.dto.HomeDTO;
import fr.vertours.buddtwo.dto.RegistrationDTO;
import fr.vertours.buddtwo.exception.EmailAlreadyPresentException;
import fr.vertours.buddtwo.model.BankAccount;
import fr.vertours.buddtwo.model.User;
import fr.vertours.buddtwo.repository.BankAccountRepository;
import fr.vertours.buddtwo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private BankAccountService bankAccountService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, BankAccountService bankAccountService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.bankAccountService = bankAccountService;
    }

    /**
     * Use only by DataBaseConfig
     * @param user
     */
    public void saveUserByUser(User user) {
        User newUser = new User(user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);
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
        if(user.getBankAccount()==null) {
            dto.setBankName("No bank has been entered in the application");
        } else {
            dto.setBankName(user.getBankAccount().getCustomizeName());
        }
        return dto;
    }

    public void saveNewBankAccount(AddBankDTO dto, MyUserDetails mUD) {
        User user = userRepository.findByEmail(mUD.getUsername());
        BankAccount bankA = bankAccountService.saveBankAccount(user, dto);
        user.setBankAccount(bankA);
        userRepository.save(user);

    }
}
