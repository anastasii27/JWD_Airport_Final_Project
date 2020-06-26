package by.epam.tr.dao;

import by.epam.tr.bean.User;
import java.util.Map;

public interface CrewDAO {
    boolean createCrew(String crewName, Map<String, User> users) throws DAOException;
    boolean doesCrewNameExist(String crewName) throws DAOException;
    int deleteCrew(String crewName) throws DAOException;
    User findMainPilot(String crewName) throws DAOException;
}
