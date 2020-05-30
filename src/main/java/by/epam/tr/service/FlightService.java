package by.epam.tr.service;

import by.epam.tr.bean.Flight;
import java.time.LocalDate;
import java.util.ArrayList;

public interface FlightService {

    ArrayList<Flight> userFlightsList(String surname, String email, LocalDate departureDate) throws ServiceException;
    ArrayList <Flight> allFlightsList() throws ServiceException;
    Flight flightInfo(String flightNumber, String departureDate) throws ServiceException;
}
