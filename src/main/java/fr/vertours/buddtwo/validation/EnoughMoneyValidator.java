package fr.vertours.buddtwo.validation;

import fr.vertours.buddtwo.dto.TransferDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnoughMoneyValidator implements ConstraintValidator<EnoughMoney,
        TransferDTO> {

    @Override
    public boolean isValid(
            TransferDTO dto,
            ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
