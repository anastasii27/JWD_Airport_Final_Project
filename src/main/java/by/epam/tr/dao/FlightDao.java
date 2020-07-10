package by.epam.tr.dao;

import by.epam.tr.bean.Flight;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface FlightDao {
    List<Flight> airportArrivals(Map<String, String> params) throws DaoException;//todo change with Flight
    List<Flight> airportDepartures(Map<String, String> params) throws DaoException;//todo change with Flight
    Flight flightInfo(String flightNumber, LocalDate departureDate) throws DaoException;
    int createFlight(Flight flight) throws DaoException;
    boolean doesFlightNumberExist(String flightNumber, LocalDate date) throws DaoException;
    List<Flight> allFlightByDay(LocalDate departureDate) throws DaoException;
    int deleteFlight(String flightNumber, LocalDate departureDate) throws DaoException;
    int editFlight(Flight flight) throws DaoException;
}

