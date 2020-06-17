package by.epam.tr.service.validation.impl;

import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.dao.UserDAO;
import by.epam.tr.service.validation.ValidationPattern;
import by.epam.tr.service.validation.Validator;
import java.util.*;

public class RegistrationValidation extends Validator {

    private List<String> result= new ArrayList<>();

    @Override
    public List<String> validate(Map<String, String> params) {

        result.clear();

        if(!emptyValueCheck(params)){
            result.add("You didnt` enter some values");
            return result;
        }

        userCheck(params);
        passwordCheck(params.get("user_password"));

        try {
            loginCheck(params.get("login"));
        } catch (DAOException e) {
            //
        }

        return result;
    }

    private void userCheck(Map<String, String> params){

        if(!checkWithPattern(ValidationPattern.TEXT_PATTERN,params.get("user_name"))){
            result.add("Illegal name! Only letters allowed");
        }

        if(!checkWithPattern(ValidationPattern.TEXT_PATTERN,params.get("surname"))){
            result.add("Illegal surname! Only letters allowed");
        }

        if(!checkWithPattern(ValidationPattern.EMAIL_PATTERN,params.get("email"))){
            result.add("Illegal email!");
        }

        if(!checkWithPattern(ValidationPattern.NUMBER_PATTERN,params.get("career_start_year"))){
            result.add("Illegal career start year!");
        }
    }

    private void passwordCheck(String password){

        if(!checkWithPattern(ValidationPattern.NO_SIGN_PATTERN, password)){
            result.add("Illegal password! Signs not allowed");
        }

        if(!lengthCheck(6,15, password)){
            result.add("Illegal password length");
        }
    }

    private void loginCheck(String login) throws DAOException {

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

        if(userDAO.doesUserExist(login)){
            result.add("User with this login is already exist!");
        }

        if(!checkWithPattern(ValidationPattern.NO_SIGN_PATTERN, login)){
            result.add("Illegal login! Signs not allowed");
        }

        if(!lengthCheck(6,15, login)){
            result.add("Illegal login length");
        }
    }
}
