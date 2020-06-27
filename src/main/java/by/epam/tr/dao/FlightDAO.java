package by.epam.tr.dao;

import by.epam.tr.bean.Flight;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface FlightDAO {

    List<Flight> flightsByDay(Map<String, String> params) throws DAOException;
    Flight flightInfo(String flightNumber, String departureDate) throws DAOException;
    int createFlight(Flight flight) throws DAOException;
}
