package by.epam.tr.service;

import by.epam.tr.bean.User;
import java.util.ArrayList;

public interface UserService {

    User signIn(String login, String password) throws ServiceException;
    int registration(User user, String login, String password) throws ServiceException;
    ArrayList<User> userByGroup(String groupName) throws ServiceException;
}
