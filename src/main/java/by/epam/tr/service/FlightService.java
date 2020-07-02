package by.epam.tr.service;

import by.epam.tr.bean.Flight;
import java.util.List;
import java.util.Map;

public interface FlightService {
    List <Flight> airportFlightsByDay(Map<String, String> params) throws ServiceException;
    Flight flightInfo(String flightNumber, String departureDate) throws ServiceException;
    boolean createFlight(Flight flight) throws ServiceException;
    List<Flight> allFlightByDay(Map<String, String> params) throws ServiceException;
    boolean doesFlightNumberExist(String flightNumber) throws ServiceException;
}
