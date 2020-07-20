package by.epam.airport_system.dao;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UserFlightsDao{
    List<Flight> userFlights(Map<String, String > params) throws DaoException;//todo change with object
    List<Flight> nearestUserFlights(String surname, String email, LocalDate lastDayOfRange) throws DaoException;
    List<Flight> dispatcherFlights(String surname, String email) throws DaoException;
    Flight lastUserFlightBeforeDate(User user, LocalDate date) throws DaoException;
    Flight firstUserFlightAfterDate(User user, LocalDate date) throws DaoException;
}
