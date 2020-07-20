package by.epam.airport_system.service.validation.impl;

import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.dao.UserDao;
import by.epam.airport_system.service.validation.UserValidator;
import by.epam.airport_system.service.validation.ValidationPattern;
import by.epam.airport_system.service.validation.ValidationResult;
import lombok.extern.log4j.Log4j2;
import java.util.*;

@Log4j2
public class CreatedUserValidation extends UserValidator {
    private final static String USER_PASSWORD_PARAM = "user_password";
    private final static String USER_LOGIN_PARAM = "login";
    private final static String KEY6= "local.validation.user.5";
    private final static String KEY7= "local.validation.user.6";

    @Override
    public ValidationResult validate(Map<String, String> params) {
        ValidationResult result = getValidationResult(params);

        if(!emptyValueCheck(params)){
            result.addError(KEY1);
            return result;
        }

        if(!checkName(params.get(USER_NAME_PARAM))){
            result.addError(KEY2);
        }

        if(!checkSurname(params.get(USER_SURNAME_PARAM))){
            result.addError(KEY3);
        }

        if(!checkEmail(params.get(USER_EMAIL_PARAM))){
            result.addError(KEY4);
        }

        if(!checkCareerStartYear(params.get(USER_CAREER_START_YEAR_PARAM))){
            result.addError(KEY5);
        }

        if(!passwordCheck(params.get(USER_PASSWORD_PARAM))){
            result.addError(KEY6);
        }

        try {
            if(!loginCheck(params.get(USER_LOGIN_PARAM))){
                result.addError(KEY7);
            }
        } catch (DaoException e) {
            log.error("Exception during login check");
        }

        return result;
    }

    private boolean passwordCheck(String password){
        return checkWithPattern(ValidationPattern.NO_SIGN_PATTERN, password)
                    && lengthCheck(6, 15, password);
    }

    private boolean loginCheck(String login) throws DaoException {
        UserDao userDAO = DaoFactory.getInstance().getUserDAO();

        if(userDAO.doesUserExist(login)){
            return false;
        }

        if(!checkWithPattern(ValidationPattern.NO_SIGN_PATTERN, login)){
            return false;
        }

        if(!lengthCheck(4,15, login)){
            return false;
        }
        return true;
    }
}

