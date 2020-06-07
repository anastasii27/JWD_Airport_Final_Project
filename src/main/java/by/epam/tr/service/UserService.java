package by.epam.tr.service;

import by.epam.tr.bean.User;
import java.util.List;

public interface UserService {

    User signIn(String login, String password) throws ServiceException;
    int registration(User user, String login, String password) throws ServiceException;
    List<User> userByGroup(String groupName) throws ServiceException;
}
