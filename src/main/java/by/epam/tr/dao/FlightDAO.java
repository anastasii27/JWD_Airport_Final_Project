package by.epam.tr.dao;

import by.epam.tr.bean.Flight;
import java.util.ArrayList;

public interface FlightDAO {
    ArrayList<Flight>  userFlightsList(String login) throws DAOException;
    ArrayList <Flight> allFlightsList();
}
