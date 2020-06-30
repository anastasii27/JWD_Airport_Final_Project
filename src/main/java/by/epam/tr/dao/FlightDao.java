package by.epam.tr.dao;

import by.epam.tr.bean.Flight;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface FlightDao {
    List<Flight> airportArrivals(Map<String, String> params) throws DaoException;
    List<Flight> airportDepartures(Map<String, String> params) throws DaoException;
    Flight flightInfo(String flightNumber, String departureDate) throws DaoException;
    int createFlight(Flight flight) throws DaoException;
    boolean doesFlightNumberExist(String flightNumber) throws DaoException;
    List<Flight> allFlightByDay(String departureDate) throws DaoException;
}

