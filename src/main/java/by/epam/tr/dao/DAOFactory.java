package by.epam.tr.dao;

import by.epam.tr.dao.impl.FlightDAOImpl;
import by.epam.tr.dao.impl.GroupDAOImpl;
import by.epam.tr.dao.impl.UserDAOImpl;

public final class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();

    private final UserDAO userDao = new UserDAOImpl();
    private final FlightDAO flightDAO = new FlightDAOImpl();
    private final GroupDAO groupDAO = new GroupDAOImpl();

    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDao;
    }

    public FlightDAO getFlightDAO(){
        return flightDAO;
    }

    public GroupDAO getGroupDAO(){
        return groupDAO;
    }
}