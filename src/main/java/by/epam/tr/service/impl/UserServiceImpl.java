package by.epam.tr.service.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.DaoFactory;
import by.epam.tr.dao.ListCreatorDao;
import by.epam.tr.dao.UserDao;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.UserService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final static String USER_ROLE = "dispatcher";
    private UserDao dao = DaoFactory.getInstance().getUserDAO();

    @Override
    public User signIn(String login, String password) throws ServiceException {
        try {
            return dao.signIn(login,password);
        } catch (DaoException e) {
            throw new ServiceException("Exception during signing in!", e);
        }
    }

    @Override
    public boolean userRegistration(User user, String login, String password) throws ServiceException {
        try {
           return dao.addNewUser(user, login, password);
        } catch (DaoException e) {
            throw new ServiceException("Exception during registration!", e);
        }
    }

    @Override
    public List<User> freeDispatchers(LocalDate date, LocalTime time, String airportName) throws ServiceException {
        ListCreatorDao listCreatorDAO = DaoFactory.getInstance().getListCreatorDAO();

        try {
            List<User> allDispatchers = listCreatorDAO.createUserByRoleList(USER_ROLE);
            List<User> busyArrivalDispatchers = dao.busyArrivalDispatchers(date, time, airportName);
            List<User> busyDepartureDispatchers = dao.busyDepartureDispatchers(date, time, airportName);

            return findFreeDispatchers(allDispatchers, busyArrivalDispatchers, busyDepartureDispatchers);
        } catch (DaoException e) {
            throw new ServiceException("Exception during free dispatcher searching!", e);
        }
    }

    private List<User> findFreeDispatchers(List<User> allDispatchers, List<User> busyArrivalDispatchers, List<User> busyDepartureDispatchers){
        allDispatchers.removeAll(busyArrivalDispatchers);
        allDispatchers.removeAll(busyDepartureDispatchers);

        return allDispatchers;
    }
}
