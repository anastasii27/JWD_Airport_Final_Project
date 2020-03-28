package by.epam.tr.service;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.User;
import java.util.ArrayList;

public interface UserService {
    String singIn(String login, String password) throws ServiceException;
    String registration(User user) throws ServiceException;
}
