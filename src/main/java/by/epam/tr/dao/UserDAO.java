package by.epam.tr.dao;

import by.epam.tr.bean.User;

import java.util.ArrayList;

public interface UserDAO {
    boolean addNewUser(User user) throws DAOException;
    ArrayList<String> getUserInfo(String info) throws DAOException;
}
