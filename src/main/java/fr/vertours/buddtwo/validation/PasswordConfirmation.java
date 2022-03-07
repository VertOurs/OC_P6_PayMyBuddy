package fr.vertours.buddtwo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordConfirmationValidator.class)
public @interface PasswordConfirmation {

    String message() default "the password and the confirmation password must "
            + "be strictly identical.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
