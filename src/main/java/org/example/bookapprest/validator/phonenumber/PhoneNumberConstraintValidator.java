package org.example.bookapprest.validator.phonenumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.bookapprest.validator.NumberChecker;


public class PhoneNumberConstraintValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {

        boolean result = false;
        String[] mobileOperatorPrefixes = new String[]{"10", "50", "51", "55", "70", "77", "99"};
        String mobileNumberOperatorPart = phoneNumber.substring(4, 6);

        if (phoneNumber.length() != 13 && !phoneNumber.startsWith("+994") && !NumberChecker.isNumeric(phoneNumber.substring(1,13))) {
            return result;
        } else {
            for (String operator : mobileOperatorPrefixes) {
                if (mobileNumberOperatorPart.equals(operator)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
