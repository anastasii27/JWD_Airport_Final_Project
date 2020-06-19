package by.epam.tr.service;

import by.epam.tr.bean.User;
import java.util.List;
import java.util.Map;

public interface CrewService {

    List<User> crewMembers(String crewName) throws ServiceException;
    boolean createCrew(String crewName, Map<String, User> users) throws ServiceException;
    boolean doesCrewNameExist(String crewName) throws ServiceException;
    void deleteCrew(String crewName) throws ServiceException;
    void deleteCrewMember(String crewName, User user) throws ServiceException;
}
