package by.epam.tr.service;

import by.epam.tr.bean.User;

public interface UserService {

    User signIn(String login, String password) throws ServiceException;
    boolean userRegistration(User user, String login, String password) throws ServiceException;
}
