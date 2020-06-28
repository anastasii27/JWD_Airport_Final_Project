package by.epam.tr.dao;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface UserFlightsDao{
    List<Flight> userFlights(Map<String, String > params) throws DAOException;
    List<Flight> nearestUserFlights(String surname, String email, LocalDate lastDayOfRange) throws DAOException;
    List<Flight> dispatcherFlights(String surname, String email) throws DAOException;
    List<User> freeDispatchers(LocalDate date, LocalTime time);
}
