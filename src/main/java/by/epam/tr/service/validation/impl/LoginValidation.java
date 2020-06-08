package by.epam.tr.service.validation.impl;

import by.epam.tr.service.validation.ValidationPattern;
import by.epam.tr.service.validation.Validator;

public class LoginValidation extends Validator {

    private final static int MIN_LEN = 4;
    private final static int MAX_LEN = 15;

    @Override
    public boolean validate(Object...object) {

        String login;
        
        for (Object obj : object) {

            login = (String) obj;

            if (!nullCheck(login)) {
                return false;
            }

            if (!checkWithPattern(ValidationPattern.NO_SIGN_PATTERN, login)) {
                return false;
            }

            if(!lengthCheck(MIN_LEN,MAX_LEN, login)){
                return false;
            }
        }
        return true;
    }
}
