package org.example.bookapprest.validator.isbn;


import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsbnConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Isbn {

    String message() default "[ISBN code should be 13 number digit]";

    Class[] groups() default {};

    Class[] payload() default {};
}
