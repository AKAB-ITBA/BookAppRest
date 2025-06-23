package org.example.bookapprest.validator.isbn;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.bookapprest.validator.NumberChecker;

public class IsbnConstraintValidator implements ConstraintValidator<Isbn, String> {

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
        if (isbn.length() != 13) {
            System.out.println("not 13");
            return false;
        } else if (!NumberChecker.isNumeric(isbn)) {
            System.out.println("not numeric");
            return false;
        }
        return true;

        /*
        boolean result = false;
        if (isbn.length() != 13 && !NumberChecker.isNumeric(isbn)) {
            if (isbn.length() != 13) {
                System.out.println("not 13");
            } else if (!NumberChecker.isNumeric(isbn)) {
                System.out.println("not numeric");
            }
            return result;
        } else {
            return true;
        }*/
    }
}

