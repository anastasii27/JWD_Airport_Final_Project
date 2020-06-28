package by.epam.tr.service.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.DaoFactory;
import by.epam.tr.dao.UserDao;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao dao = DaoFactory.getInstance().getUserDAO();

    @Override
    public User signIn(String login, String password) throws ServiceException {
        User user;

        try {
            user = dao.signIn(login,password);
        } catch (DaoException e) {
            throw new ServiceException("Exception during signing in!", e);
        }
        return user;
    }

    @Override
    public boolean userRegistration(User user, String login, String password) throws ServiceException {
        boolean operationResult;

        try {
           operationResult = dao.addNewUser(user, login, password);
        } catch (DaoException e) {
            throw new ServiceException("Exception during registration!", e);
        }
        return operationResult;
    }
}
