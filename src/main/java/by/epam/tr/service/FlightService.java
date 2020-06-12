package by.epam.tr.service;

import by.epam.tr.bean.Flight;
import java.time.LocalDate;
import java.util.List;

public interface FlightService {

    List<Flight> userFlights(String surname, String email, LocalDate departureDate) throws ServiceException;
    List <Flight> flightsByDay(LocalDate departureDate, String airportName, String type) throws ServiceException;
    Flight flightInfo(String flightNumber, String departureDate) throws ServiceException;
    List<Flight> nearestUserFlights(String surname, String email) throws ServiceException;
}
