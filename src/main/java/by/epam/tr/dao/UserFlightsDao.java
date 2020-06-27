package by.epam.tr.dao;

import by.epam.tr.bean.Flight;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UserFlightsDao{
    List<Flight> userFlights(Map<String, String > params) throws DAOException;
    List<Flight> nearestUserFlights(String surname, String email, LocalDate lastDayOfRange) throws DAOException;
    List<Flight> dispatcherFlights(String surname, String email) throws DAOException;
}
