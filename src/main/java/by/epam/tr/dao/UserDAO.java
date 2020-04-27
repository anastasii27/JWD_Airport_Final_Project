package by.epam.tr.dao;

import by.epam.tr.bean.Group;
import by.epam.tr.bean.User;
import java.util.ArrayList;

public interface UserDAO {

    boolean addNewUser(User user, String login, String password) throws DAOException;
    User singIn(String login, String password) throws DAOException;
    ArrayList<User> allUsersInfo() throws DAOException;
    ArrayList <Group> userGroups(String login) throws DAOException;
    ArrayList<User> userByGroup(String groupName) throws DAOException;
    boolean doesUserExist(String login) throws DAOException;
  // ArrayList <String> getUserFlights(String login);
}
