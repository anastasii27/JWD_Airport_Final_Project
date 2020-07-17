package by.epam.tr.service.validation.impl;

import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.DaoFactory;
import by.epam.tr.dao.UserDao;
import by.epam.tr.service.validation.ValidationPattern;
import by.epam.tr.service.validation.ValidationResult;
import by.epam.tr.service.validation.Validator;
import lombok.extern.log4j.Log4j2;
import java.util.*;

@Log4j2
public class RegistrationValidation implements Validator {
    private final static String USER_NAME_PARAM = "user_name";
    private final static String USER_SURNAME_PARAM = "surname";
    private final static String USER_EMAIL_PARAM = "email";
    private final static String USER_CAREER_START_YEAR_PARAM = "career_start_year";
    private final static String USER_PASSWORD_PARAM = "user_password";
    private final static String USER_LOGIN_PARAM = "login";
    private final static String LOCAL_PARAM = "local";
    private final static String KEY1= "local.validation.edit.1";
    private final static String KEY2= "local.validation.user.1";
    private final static String KEY3= "local.validation.user.2";
    private final static String KEY4= "local.validation.user.3";
    private final static String KEY5= "local.validation.user.4";
    private final static String KEY6= "local.validation.user.5";
    private final static String KEY7= "local.validation.user.7";


    @Override
    public ValidationResult validate(Map<String, String> params) {
        ValidationResult result = getValidationResult(params);

        if(!emptyValueCheck(params)){
            result.addMessage(KEY1);
            return result;
        }

        if(!checkWithPattern(ValidationPattern.TEXT_PATTERN, params.get(USER_NAME_PARAM))){
            result.addMessage(KEY2);
        }

        if(!checkWithPattern(ValidationPattern.TEXT_PATTERN, params.get(USER_SURNAME_PARAM))){
            result.addMessage(KEY3);
        }

        if(!checkWithPattern(ValidationPattern.EMAIL_PATTERN, params.get(USER_EMAIL_PARAM))){
            result.addMessage(KEY4);
        }

        if(!checkWithPattern(ValidationPattern.NUMBER_PATTERN, params.get(USER_CAREER_START_YEAR_PARAM))){
            result.addMessage(KEY5);
        }

        if(!passwordCheck(params.get(USER_PASSWORD_PARAM))){
            result.addMessage(KEY6);
        }

        try {
            if(!loginCheck(params.get(USER_LOGIN_PARAM))){
                result.addMessage(KEY7);
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

    private ValidationResult getValidationResult(Map<String,String> params){
        String lang = params.get(LOCAL_PARAM);
        ValidationResult result;

        if(lang == null){
            result = new ValidationResult();
        }else {
            result = new ValidationResult(lang);
        }
        return result;
    }
}

