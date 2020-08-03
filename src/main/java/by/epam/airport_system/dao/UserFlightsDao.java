package by.epam.airport_system.dao;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.User;
import java.time.LocalDate;
import java.util.List;

public interface UserFlightsDao{
    List<Flight> userFlights(int id, LocalDate date) throws DaoException;
    List<Flight> dispatcherFlights(String surname, String email) throws DaoException;
    Flight lastUserFlightBeforeDate(User user, LocalDate date) throws DaoException;
    Flight firstUserFlightAfterDate(User user, LocalDate date) throws DaoException;
}
