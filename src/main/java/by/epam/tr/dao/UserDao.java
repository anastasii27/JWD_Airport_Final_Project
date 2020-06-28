package by.epam.tr.dao;

import by.epam.tr.bean.User;

import java.util.List;

public interface UserDao {
    boolean addNewUser(User user, String login, String password) throws DaoException;
    User signIn(String login, String password) throws DaoException;
    boolean doesUserExist(String login) throws DaoException;
    List<User> allUsers() throws DaoException;
}
