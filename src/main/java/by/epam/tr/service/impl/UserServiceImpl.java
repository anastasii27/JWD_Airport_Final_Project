package by.epam.tr.service.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.dao.UserDAO;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDAO  dao = DAOFactory.getInstance().getUserDAO();

    @Override
    public User signIn(String login, String password) throws ServiceException {

        User user;
        try {
            user = dao.singIn(login,password);

        } catch (DAOException e) {
            throw new ServiceException("Exception during signing in!");
        }
        return user;
    }

    @Override
    public boolean userRegistration(User user, String login, String password) throws ServiceException {

        boolean operationResult;

        try {
           operationResult = dao.addNewUser(user, login, password);
        } catch (DAOException e) {
            throw new ServiceException("Exception during registration!");
        }
        return operationResult;
    }
}
