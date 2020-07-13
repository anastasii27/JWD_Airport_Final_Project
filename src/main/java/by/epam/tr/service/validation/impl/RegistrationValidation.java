package by.epam.tr.service.validation.impl;

import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.DaoFactory;
import by.epam.tr.dao.UserDao;
import by.epam.tr.service.validation.ValidationPattern;
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

    @Override
    public List<String> validate(Map<String, String> params) {
        List<String> validationResult= new ArrayList<>();

        if(!emptyValueCheck(params)){
            validationResult.add("You didnt` enter some values");
            return validationResult;
        }

        if(!checkWithPattern(ValidationPattern.TEXT_PATTERN, params.get(USER_NAME_PARAM))){
            validationResult.add("Illegal name! Only letters allowed");
        }

        if(!checkWithPattern(ValidationPattern.TEXT_PATTERN, params.get(USER_SURNAME_PARAM))){
            validationResult.add("Illegal surname! Only letters allowed");
        }

        if(!checkWithPattern(ValidationPattern.EMAIL_PATTERN, params.get(USER_EMAIL_PARAM))){
            validationResult.add("Illegal email!");
        }

        if(!checkWithPattern(ValidationPattern.NUMBER_PATTERN, params.get(USER_CAREER_START_YEAR_PARAM))){
            validationResult.add("Illegal career start year!");
        }

        if(!passwordCheck(params.get(USER_PASSWORD_PARAM))){
            validationResult.add("Illegal password");
        }

        try {
            if(!loginCheck(params.get(USER_LOGIN_PARAM))){
                validationResult.add("Illegal login");
            }
        } catch (DaoException e) {
            log.error("Exception during login check");
        }
        return validationResult;
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

