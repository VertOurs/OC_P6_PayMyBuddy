package fr.vertours.buddtwo.validation;

import fr.vertours.buddtwo.dto.ChangePasswordDTO;
import fr.vertours.buddtwo.dto.RegistrationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PassConfValidator implements ConstraintValidator <PassConf, ChangePasswordDTO>{


    @Override
    public boolean isValid(ChangePasswordDTO dto, ConstraintValidatorContext constraintValidatorContext) {
        return dto.getNewPassword().equals(dto.getNewPasswordConfirmation());
    }
}
