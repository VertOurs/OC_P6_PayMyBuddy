package fr.vertours.buddtwo.validation;

import fr.vertours.buddtwo.dto.ChangePasswordDTO;
import fr.vertours.buddtwo.dto.RegistrationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmationValidator implements ConstraintValidator <PasswordConfirmation, RegistrationDTO>{


    @Override
    public boolean isValid(RegistrationDTO regDTO, ConstraintValidatorContext constraintValidatorContext) {
        return regDTO.getPassword().equals(regDTO.getConfirmation());
    }
}
