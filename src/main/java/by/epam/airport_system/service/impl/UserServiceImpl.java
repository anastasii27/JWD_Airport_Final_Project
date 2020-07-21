package by.epam.airport_system.service.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.dao.UserDao;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.UserService;
import by.epam.airport_system.service.mailing.MailMessages;
import by.epam.airport_system.service.mailing.SmtpMailSender;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final static String USER_ROLE = "dispatcher";
    private static final String LOGIN_PATTERN ="[\\w]{4,15}";
    private final static int MAX_LOGIN_LEN = 15;
    private final static int MIN_LOGIN_LEN = 6;
    private UserDao dao = DaoFactory.getInstance().getUserDAO();

    @Override
    public User getUserByLogin(String login) throws ServiceException {
        try {
            return dao.getUserByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException("Exception during signing in!", e);
        }
    }

    @Override
    public boolean signUpUser(User user) throws ServiceException {
        try {
           return dao.signUpUser(user);
        } catch (DaoException e) {
            throw new ServiceException("Exception during registration!", e);
        }
    }

    @Override
    public List<User> freeDispatchers(LocalDate date, LocalTime time, String airportName) throws ServiceException {
        try {
            List<User> allDispatchers = userByRoleList(USER_ROLE);
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

    @Override
    public List<String> rolesList() throws ServiceException {
        try {
            return dao.rolesList();
        } catch (DaoException e) {
            throw new ServiceException("Exception during roles list creation", e);
        }
    }

    @Override
    public List<User> userByRoleList(String role) throws ServiceException {
        try {
            return dao.userByRoleList(role);
        } catch (DaoException e) {
            throw new ServiceException("Exception during users by role list creation", e);
        }
    }

    @Override
    public boolean editUser(User user) throws ServiceException {
        try {
            return dao.editUser(user) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Exception during user editing", e);
        }
    }

    @Override
    public boolean changeLogin(String login, User user) throws ServiceException {
        SmtpMailSender mailSender = SmtpMailSender.getInstance();
        try {
            if(!login.matches(LOGIN_PATTERN)){
                return false;
            }

            if(dao.changeLogin(login, user) != 0){
                mailSender.sendMail(user, MailMessages.LOGIN_CHANGE);
                return true;
            }
            return false;
        } catch (DaoException e) {
            throw new ServiceException("Exception during login changing", e);
        }
    }

    @Override
    public boolean changePassword(String password, User user) throws ServiceException {
        SmtpMailSender mailSender = SmtpMailSender.getInstance();
        try {
            if (!(password.length() >= MIN_LOGIN_LEN && password.length() <= MAX_LOGIN_LEN)) {
                return false;
            }

            if(dao.changePassword(password, user) != 0){
                mailSender.sendMail(user, MailMessages.PASSWORD_CHANGE);
                return true;
            }
            return false;
        } catch (DaoException e) {
            throw new ServiceException("Exception during password changing", e);
        }
    }
}
