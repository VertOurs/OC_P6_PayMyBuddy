package fr.vertours.buddtwo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnoughMoneyValidator.class)
public @interface EnoughMoney {

    String message() default "you do not have enough money for this operation,"
            + " please credit your account.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
