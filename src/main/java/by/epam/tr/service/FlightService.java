package by.epam.tr.service;

import by.epam.tr.bean.Flight;

import java.util.ArrayList;

public interface FlightService {
    ArrayList<Flight> userFlightsList(String login) throws ServiceException;
    ArrayList <Flight> allFlightsList();
}
