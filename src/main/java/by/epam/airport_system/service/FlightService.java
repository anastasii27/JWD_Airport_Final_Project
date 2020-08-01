package by.epam.airport_system.service;

import by.epam.airport_system.bean.Flight;
import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    List <Flight> airportFlightsByDay(String flightType, LocalDate departureDate, String airportShortName) throws ServiceException;
    Flight flightInfo(String flightNumber, LocalDate departureDate) throws ServiceException;
    boolean createFlight(Flight flight) throws ServiceException;
    List<Flight> allFlightByDay(LocalDate departureDate) throws ServiceException;
    boolean doesFlightNumberExist(String flightNumber, LocalDate date) throws ServiceException;
    boolean deleteFlight(String flightNumber, LocalDate departureDate) throws ServiceException;
    boolean editFlight(Flight flight) throws ServiceException;
    List<Flight> findFlights(String departureAirport, String destinationAirport) throws ServiceException;
}
