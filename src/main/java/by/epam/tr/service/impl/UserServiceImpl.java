package by.epam.tr.service.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.dao.UserDAO;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.UserService;
import by.epam.tr.service.validation.ValidationFactory;
import by.epam.tr.service.validation.Validator;

public class UserServiceImpl implements UserService {

    private UserDAO  dao = DAOFactory.getInstance().getUserDAO();

    @Override
    public User signIn(String login, String password) throws ServiceException {//null

        User user;

        try {
            user = dao.singIn(login,password);

        } catch (DAOException e) {
            throw new ServiceException("Exception during signing in!");
        }
        return user;
    }

    @Override
    public int userRegistration(User user, String login, String password) throws ServiceException {

        if(!registrationValidation(user, login, password)) {
            return -1;
        }
        try {
            if(dao.doesUserExist(login)){
                return 0;

            }else if(dao.addNewUser(user, login, password)){
                return 1;
            }
        } catch (DAOException e) {
            throw new ServiceException("Exception during registration!");
        }
        return -1;
    }

    private boolean registrationValidation(User user, String login, String password){

        Validator userValidation = ValidationFactory.getInstance().getUserValidation();
        Validator loginValidation = ValidationFactory.getInstance().getLoginValidation();
        Validator passwordValidation = ValidationFactory.getInstance().getPasswordValidation();

        if(! userValidation.validate(user)){
            return false;
        }
        if(! loginValidation.validate(login)){
            return false;
        }
        if( ! passwordValidation.validate(password)){
            return false;
        }
        return true;
    }
}
