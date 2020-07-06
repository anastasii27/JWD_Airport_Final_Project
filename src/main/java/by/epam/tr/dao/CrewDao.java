package by.epam.tr.dao;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.User;
import java.util.List;
import java.util.Map;

public interface CrewDao {
    boolean createCrew(String crewName, Map<String, User> users) throws DaoException;
    boolean doesCrewNameExist(String crewName) throws DaoException;
    int deleteCrew(String crewName) throws DaoException;
    User findMainPilot(String crewName) throws DaoException;
    List<String> allCrews() throws DaoException;
    int setCrewForFlight(String crewName, String flightNumber) throws DaoException;
    String flightCrew(Flight flight) throws DaoException;
}
