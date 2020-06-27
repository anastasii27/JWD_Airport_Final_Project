package by.epam.tr.service;

import by.epam.tr.service.impl.*;

public class ServiceFactory{

    private static final ServiceFactory instance = new ServiceFactory();

    private final UserService  userService = new UserServiceImpl();
    private final FlightService flightService = new FlightServiceImpl();
    private final CrewService crewService = new CrewServiceImpl();
    private final ListCreatorService listCreatorService = new ListCreatorServiceImpl();
    private final CrewMemberService crewMemberService = new CrewMemberServiceImpl();
    private final UserFlightsService userFlightsService = new UserFlightsServiceImpl();
    private final PlaneService planeService = new PlaneServiceImpl();

    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        return instance;
    }

    public UserService getUserService(){
        return userService;
    }

    public FlightService getFlightService(){
        return flightService;
    }

    public CrewService getCrewService(){
        return crewService;
    }

    public ListCreatorService getListCreatorService() {
        return listCreatorService;
    }

    public CrewMemberService getCrewMemberService() {
        return crewMemberService;
    }

    public UserFlightsService getUserFlightsService() {
        return userFlightsService;
    }

    public PlaneService getPlaneService() {
        return planeService;
    }
}