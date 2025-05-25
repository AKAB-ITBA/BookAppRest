package org.example.bookapprest.validator.phonenumber;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneNumberConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {

    String message() default "[Phone number should start with \"+994\"]"
            + "[also has to be one of Azerbaijan mobile operators prefix]";

    Class[] groups() default {};

    Class[] payload() default {};
}
