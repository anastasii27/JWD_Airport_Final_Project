package by.epam.tr.service;

import by.epam.tr.bean.User;
import java.util.List;

public interface CrewService {

    List<User> crewMembers(String crewName) throws ServiceException;
    boolean createCrew(String crewName, List<User> users) throws ServiceException;
}
