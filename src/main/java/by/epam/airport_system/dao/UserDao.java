package by.epam.airport_system.dao;

import by.epam.airport_system.bean.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface UserDao {
    boolean signUpUser(User user) throws DaoException;
    User getUserByLogin(String login) throws DaoException;
    List<User> busyDepartureDispatchers(LocalDate date, LocalTime time, String airportName) throws DaoException;
    List<User> busyArrivalDispatchers(LocalDate date, LocalTime time, String airportName) throws DaoException;
    List<User> userByRoleList(String role) throws DaoException;
    int editUser(User user) throws DaoException;
    int changeLogin(String login, User user) throws DaoException;
    int changePassword(String password, User user) throws DaoException;
}
