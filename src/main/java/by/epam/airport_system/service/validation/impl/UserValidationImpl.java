package by.epam.airport_system.service.validation.impl;

import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.dao.UserDao;
import by.epam.airport_system.service.validation.ValidationPattern;
import by.epam.airport_system.service.validation.ValidationResult;
import by.epam.airport_system.service.validation.Validator;
import lombok.extern.log4j.Log4j2;
import java.util.Map;

@Log4j2
public class UserValidationImpl extends Validator {
    private final static String USER_NAME_PARAM = "user_name";
    private final static String USER_SURNAME_PARAM = "surname";
    private final static String USER_EMAIL_PARAM = "email";
    private final static String USER_PASSWORD_PARAM = "user_password";
    private final static String USER_LOGIN_PARAM = "login";
    private final static String USER_CAREER_START_YEAR_PARAM = "career_start_year";
    private final static String ACTION_PARAM = "action";
    private final static String ACTION_NAME = "edit_user";
    private final static int PASSWORD_MIN_LEN = 6;
    private final static int LOGIN_MIN_LEN = 4;
    private final static int MAX_LEN = 15;
    private final static String KEY1= "local.validation.edit.1";
    private final static String KEY2= "local.validation.user.1";
    private final static String KEY3= "local.validation.user.2";
    private final static String KEY4= "local.validation.user.3";
    private final static String KEY5= "local.validation.user.4";
    private final static String KEY6= "local.validation.user.5";
    private final static String KEY7= "local.validation.user.6";

    @Override
    public ValidationResult validate(Map<String, String> params) {
        ValidationResult result = ValidationResult.getValidationResult(params);

        if(!emptyValueCheck(params)){
            result.addError(KEY1);
            return result;
        }

        if(!checkWithPattern(ValidationPattern.TEXT_PATTERN, params.get(USER_NAME_PARAM))){
            result.addError(KEY2);
        }

        if(!checkWithPattern(ValidationPattern.TEXT_PATTERN, params.get(USER_SURNAME_PARAM))){
            result.addError(KEY3);
        }

        if(!checkWithPattern(ValidationPattern.EMAIL_PATTERN, params.get(USER_EMAIL_PARAM))){
            result.addError(KEY4);
        }

        if(!checkWithPattern(ValidationPattern.NUMBER_PATTERN, params.get(USER_CAREER_START_YEAR_PARAM))){
            result.addError(KEY5);
        }

        if(!passwordCheck(params.get(USER_PASSWORD_PARAM))){
            result.addError(KEY6);
        }

        String action = params.get(ACTION_PARAM);
        try {
            if(!action.equals(ACTION_NAME) && !loginCheck(params.get(USER_LOGIN_PARAM))){
                result.addError(KEY7);
            }
        } catch (DaoException e) {
            log.error("Exception during login check");
        }

        return result;
    }

    private boolean passwordCheck(String password){
        return checkWithPattern(ValidationPattern.NO_SIGN_PATTERN, password)
                && lengthCheck(PASSWORD_MIN_LEN, MAX_LEN, password);
    }

    private boolean loginCheck(String login) throws DaoException {
        UserDao userDAO = DaoFactory.getInstance().getUserDAO();

        if(userDAO.getUserByLogin(login)!= null){
            return false;
        }

        if(!checkWithPattern(ValidationPattern.NO_SIGN_PATTERN, login)){
            return false;
        }

        if(!lengthCheck(LOGIN_MIN_LEN, MAX_LEN, login)){
            return false;
        }
        return true;
    }
}
