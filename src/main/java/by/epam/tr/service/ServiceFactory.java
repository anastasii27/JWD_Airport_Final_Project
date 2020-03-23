package by.epam.tr.service;

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