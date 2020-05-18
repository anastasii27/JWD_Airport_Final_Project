package by.epam.tr.service;

import by.epam.tr.service.impl.FlightServiceImpl;
import by.epam.tr.service.impl.GroupServiceImpl;
import by.epam.tr.service.impl.UserServiceImpl;

public class ServiceFactory{

    private static final ServiceFactory instance = new ServiceFactory();

    private final UserService  userService = new UserServiceImpl();
    private final FlightService flightService = new FlightServiceImpl();
    private final GroupService groupService = new GroupServiceImpl();

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

    public GroupService getGroupService(){
        return groupService;
    }
}