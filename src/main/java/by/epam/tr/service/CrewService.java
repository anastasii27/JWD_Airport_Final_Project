package by.epam.tr.service;

import by.epam.tr.bean.User;
import by.epam.tr.dao.DAOException;
import java.util.List;

public interface CrewService {

    List<User> crewMembers(String crewName) throws ServiceException;
    boolean createCrew(String crewName) throws ServiceException;
    boolean addMemberToCrew(List<User> users) throws DAOException;
}
