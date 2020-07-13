package by.epam.tr.dao;

import by.epam.tr.bean.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface UserDao {
    boolean signUpUser(User user) throws DaoException;
    User getUserByLogin(String login) throws DaoException;
    boolean doesUserExist(String login) throws DaoException;
    List<User> busyDepartureDispatchers(LocalDate date, LocalTime time, String airportName) throws DaoException;
    List<User> busyArrivalDispatchers(LocalDate date, LocalTime time, String airportName) throws DaoException;
    List<String> rolesList() throws DaoException;
    List<User> userByRoleList(String role) throws DaoException;
}
