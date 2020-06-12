package by.epam.tr.dao;

import by.epam.tr.bean.Flight;
import java.time.LocalDate;
import java.util.List;

public interface FlightDAO {

    List<Flight> userFlights(String surname, String email, LocalDate departureDate) throws DAOException;
    List<Flight> flightsByDay(LocalDate departureDate, String airportName, String type) throws DAOException;
    Flight flightInfo(String flightNumber, String departureDate) throws DAOException;
    List<Flight> nearestUserFlights(String surname, String email, LocalDate lastDayOfRange) throws DAOException;
}
