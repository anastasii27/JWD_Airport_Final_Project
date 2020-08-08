package by.epam.airport_system.service;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.User;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CrewService {
    boolean createCrew(String crewName, Map<String, User> users) throws ServiceException;
    boolean doesCrewExist(String crewName) throws ServiceException;
    boolean deleteCrew(String crewName) throws ServiceException;
    List<String> crewsList() throws ServiceException;
    Set<String> findFreeCrewsForFlight(Flight flight) throws ServiceException;
    boolean setCrewForFlight(String crewName, String flightNumber) throws ServiceException;
    String flightCrew(Flight flight)  throws ServiceException;
}
