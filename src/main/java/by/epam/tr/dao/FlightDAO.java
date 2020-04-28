package by.epam.tr.dao;

import by.epam.tr.bean.Flight;
import java.util.ArrayList;

public interface FlightDAO {

    ArrayList<Flight> userFlightsList(String login) throws DAOException;
    ArrayList<Flight> allFlightsList() throws DAOException;
    Flight flightInfo(String flightNumber, String departureDate);
}
