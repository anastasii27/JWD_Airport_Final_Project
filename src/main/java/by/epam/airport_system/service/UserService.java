package by.epam.airport_system.service;

import by.epam.airport_system.bean.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface UserService {
    User getUserByLogin(String login) throws ServiceException;
    boolean signUpUser(User user) throws ServiceException;
    List<User> freeDispatchers(LocalDate date, LocalTime time, String airportName) throws ServiceException;
    List<String> rolesList() throws ServiceException;
    List<User> userByRoleList(String role) throws ServiceException;
    boolean editUser(User user) throws ServiceException;
}

