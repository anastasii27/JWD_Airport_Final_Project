package by.epam.tr.service;

import by.epam.tr.service.impl.UserServiceImpl;

public class ServiceFactory{

    private static final ServiceFactory instance = new ServiceFactory();

    private final UserService  userServiceImpl = new UserServiceImpl();

    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        return instance;
    }

    public UserService getUserDAO(){
        return userServiceImpl;
    }
}