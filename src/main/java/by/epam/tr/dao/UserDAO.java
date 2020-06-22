package by.epam.tr.dao;

import by.epam.tr.bean.User;
import java.util.List;

public interface UserDAO {

    boolean addNewUser(User user, String login, String password) throws DAOException;
    User signIn(String login, String password) throws DAOException;
    boolean doesUserExist(String login) throws DAOException;
    boolean doesUserExist(User user) throws DAOException;
    List<User> allUsers() throws DAOException;
}
