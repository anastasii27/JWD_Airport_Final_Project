package by.epam.tr.dao;

import by.epam.tr.bean.User;

public interface UserDAO {

    boolean addNewUser(User user, String login, String password) throws DAOException;
    User singIn(String login, String password) throws DAOException;
    boolean doesUserExist(String login) throws DAOException;
}
