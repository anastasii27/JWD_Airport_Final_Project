package by.epam.tr.dao;

import by.epam.tr.dao.impl.*;

public final class DAOFactory {
    private final static DAOFactory instance = new DAOFactory();
    private final UserDAO userDao = new UserDAOImpl();
    private final FlightDAO flightDAO = new FlightDAOImpl();
    private final CrewDAO crewDAO = new CrewDAOImpl();
    private final CrewMemberDAO crewMemberDAO = new CrewMemberDAOImpl();
    private final ListCreatorDAO listCreatorDAO = new ListCreatorImpl();
    private final UserFlightsDao userFlightsDao = new UserFlightsDaoImpl();
    private final PlaneDao planeDao = new PlaneDaoImpl();

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

    public UserFlightsDao getUserFlightsDao() {
        return userFlightsDao;
    }

    public PlaneDao getPlaneDao() {
        return planeDao;
    }
}