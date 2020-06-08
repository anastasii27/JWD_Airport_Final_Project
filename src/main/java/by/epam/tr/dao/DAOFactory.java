package by.epam.tr.dao;

import by.epam.tr.dao.impl.CrewDAOImpl;
import by.epam.tr.dao.impl.FlightDAOImpl;
import by.epam.tr.dao.impl.ListCreationImpl;
import by.epam.tr.dao.impl.UserDAOImpl;

public final class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();

    private final UserDAO userDao = new UserDAOImpl();
    private final FlightDAO flightDAO = new FlightDAOImpl();
    private final CrewDAO crewDAO = new CrewDAOImpl();
    private final ListCreationDAO listCreationDAO = new ListCreationImpl();

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

    public CrewDAO getCrewDAO(){
        return crewDAO;
    }

    public ListCreationDAO getListCreationDAO() {
        return listCreationDAO;
    }
}