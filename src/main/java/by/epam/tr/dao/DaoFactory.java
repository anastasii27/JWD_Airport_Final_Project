package by.epam.tr.dao;

import by.epam.tr.dao.impl.*;

public final class DaoFactory {
    private final static DaoFactory instance = new DaoFactory();
    private final UserDao userDao = new UserDaoImpl();
    private final FlightDao flightDao = new FlightDaoImpl();
    private final CrewDao crewDao = new CrewDaoImpl();
    private final CrewMemberDao crewMemberDao = new CrewMemberDaoImpl();
    private final ListCreatorDao listCreatorDao = new ListCreatorIDaoImpl();
    private final UserFlightsDao userFlightsDao = new UserFlightsDaoImpl();
    private final PlaneDao planeDao = new PlaneDaoImpl();

    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return instance;
    }

    public UserDao getUserDAO() {
        return userDao;
    }

    public FlightDao getFlightDAO(){
        return flightDao;
    }

    public CrewDao getCrewDAO(){
        return crewDao;
    }

    public ListCreatorDao getListCreatorDAO() {
        return listCreatorDao;
    }

    public CrewMemberDao getCrewMemberDAO() {
        return crewMemberDao;
    }

    public UserFlightsDao getUserFlightsDao() {
        return userFlightsDao;
    }

    public PlaneDao getPlaneDao() {
        return planeDao;
    }
}