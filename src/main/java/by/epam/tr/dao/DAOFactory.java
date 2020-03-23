package by.epam.tr.dao;

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