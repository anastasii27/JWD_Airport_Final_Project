package by.epam.tr.dao;

import by.epam.tr.dao.impl.UserDAOImpl;

public final class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();

    private final UserDAO userDao = new UserDAOImpl();

    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDao;
    }
}