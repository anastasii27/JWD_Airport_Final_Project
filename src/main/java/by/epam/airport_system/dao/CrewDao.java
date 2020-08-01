package by.epam.airport_system.dao;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.User;
import java.util.List;
import java.util.Map;

public interface CrewDao {
    boolean createCrew(String crewName, Map<String, User> users) throws DaoException;
    boolean doesCrewNameExist(String crewName) throws DaoException;
    int deleteCrew(String crewName) throws DaoException;
    List<String> allCrews() throws DaoException;
    int setCrewForFlight(String crewName, String flightNumber) throws DaoException;
    String flightCrew(Flight flight) throws DaoException;
    List<String> takenOnFlightsCrews() throws DaoException;
}
