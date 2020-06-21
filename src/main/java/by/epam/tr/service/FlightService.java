package by.epam.tr.service;

import by.epam.tr.bean.Flight;
import java.util.List;
import java.util.Map;

public interface FlightService {

    List<Flight> userFlights(Map<String, String > params) throws ServiceException;
    List <Flight> flightsByDay(Map<String, String> params) throws ServiceException;
    Flight flightInfo(String flightNumber, String departureDate) throws ServiceException;
    List<Flight> nearestUserFlights(String surname, String email) throws ServiceException;
    List<Flight> dispatcherFlights(String surname, String email) throws ServiceException;
}
