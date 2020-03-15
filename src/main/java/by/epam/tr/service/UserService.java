package by.epam.tr.service;

import by.epam.tr.bean.User;

public interface UserService {
    boolean singIn(String login, String password) throws ServiceException;
    String registration(User user) throws ServiceException;
}
