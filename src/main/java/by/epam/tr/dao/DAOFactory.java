package by.epam.tr.dao;

import by.epam.tr.dao.impl.*;

public final class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();

    private final UserDAO userDao = new UserDAOImpl();
    private final FlightDAO flightDAO = new FlightDAOImpl();
    private final CrewDAO crewDAO = new CrewDAOImpl();
    private final CrewMemberDAO crewMemberDAO = new CrewMemberImpl();
    private final ListCreatorDAO listCreatorDAO = new ListCreatorImpl();

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

    public ListCreatorDAO getListCreatorDAO() {
        return listCreatorDAO;
    }

    public CrewMemberDAO getCrewMemberDAO() {
        return crewMemberDAO;
    }
}