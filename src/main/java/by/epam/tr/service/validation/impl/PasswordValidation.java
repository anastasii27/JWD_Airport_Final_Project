package by.epam.tr.service.validation.impl;

import by.epam.tr.service.validation.ValidationPattern;
import by.epam.tr.service.validation.Validator;

public class PasswordValidation extends Validator {

    private final static int MIN_LEN = 6;
    private final static int MAX_LEN = 15;

    @Override
    public boolean validate(Object object) {

        String password = (String) object;

        if (!nullCheck(password)) {
            return false;
        }

        if (!checkWithPattern(ValidationPattern.NO_SIGN_PATTERN, password)) {
            return false;
        }

        if(!lengthCheck(MIN_LEN,MAX_LEN, password)){
            return false;
        }

        return true;
    }
}
