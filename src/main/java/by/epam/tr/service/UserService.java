package by.epam.tr.service;

import by.epam.tr.bean.Group;
import by.epam.tr.bean.User;
import java.util.ArrayList;

public interface UserService {
    String singIn(String login, String password) throws ServiceException;
    String registration(User user) throws ServiceException;
    ArrayList<Group> getUserGroups(String login) throws ServiceException;
    int getUserInfo(String login) throws ServiceException;
    ArrayList<User> getAllUsersInfo() throws ServiceException;
}
