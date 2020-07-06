package by.epam.tr.service;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.User;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CrewService {
    boolean createCrew(String crewName, Map<String, User> users) throws ServiceException;
    boolean doesCrewNameExist(String crewName) throws ServiceException;
    boolean deleteCrew(String crewName) throws ServiceException;
    User findMainPilot(String crewName) throws ServiceException;
    List<String> allCrews() throws ServiceException;
    Set<String> findFreeCrewsForFlight(Flight flight) throws ServiceException;
    boolean setCrewForFlight(String crewName, String flightNumber) throws ServiceException;
    String flightCrew(Flight flight)  throws ServiceException;
}
