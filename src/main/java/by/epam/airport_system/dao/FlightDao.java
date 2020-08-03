package by.epam.airport_system.dao;

import by.epam.airport_system.bean.Flight;
import java.time.LocalDate;
import java.util.List;

public interface FlightDao {
    List<Flight> airportArrivals(LocalDate departureDate, String airportShortName) throws DaoException;
    List<Flight> airportDepartures(LocalDate departureDate, String airportShortName) throws DaoException;
    Flight flightInfo(String flightNumber, LocalDate departureDate) throws DaoException;
    int createFlight(Flight flight) throws DaoException;
    boolean doesFlightNumberExist(String flightNumber, LocalDate date) throws DaoException;
    List<Flight> allFlightByDay(LocalDate departureDate) throws DaoException;
    int deleteFlight(String flightNumber, LocalDate departureDate) throws DaoException;
    int editFlight(Flight flight) throws DaoException;
    List<Flight> findFlight(String departureAirport, String destinationAirport, LocalDate tillDate) throws DaoException;
}

