package by.epam.tr.dao;

import by.epam.tr.bean.Group;
import by.epam.tr.bean.User;

import java.util.ArrayList;

public interface UserDAO {
    boolean addNewUser(User user) throws DAOException;
    ArrayList<User> getUsersInfo() throws DAOException;
    boolean singIn(String login, String password) throws DAOException;
    ArrayList <Group> getUserGroups(String login) throws DAOException;
    ArrayList <String> getUserFlights(String login);
}
