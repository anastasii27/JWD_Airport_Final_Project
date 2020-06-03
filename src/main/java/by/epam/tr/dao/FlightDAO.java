package by.epam.tr.dao;

import by.epam.tr.bean.Flight;
import java.time.LocalDate;
import java.util.ArrayList;

public interface FlightDAO {

    ArrayList<Flight> userFlightsList(String surname, String email, LocalDate departureDate) throws DAOException;
    ArrayList<Flight> allFlightsList(LocalDate departureDate) throws DAOException;
    Flight flightInfo(String flightNumber, String departureDate) throws DAOException;
}
