package by.epam.tr.service;

import by.epam.tr.bean.User;
import java.util.Map;

public interface CrewService {
    boolean createCrew(String crewName, Map<String, User> users) throws ServiceException;
    boolean doesCrewNameExist(String crewName) throws ServiceException;
    boolean deleteCrew(String crewName) throws ServiceException;
    User findMainPilot(String crewName) throws ServiceException;
}
