package by.epam.tr.dao;

import by.epam.tr.bean.Flight;

import java.util.List;
import java.util.Map;

public interface FlightDao {
    List<Flight> flightsByDay(Map<String, String> params) throws DaoException;
    Flight flightInfo(String flightNumber, String departureDate) throws DaoException;
    int createFlight(Flight flight) throws DaoException;
}
