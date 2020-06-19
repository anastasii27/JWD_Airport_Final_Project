package by.epam.tr.dao;

import by.epam.tr.bean.User;
import java.util.List;
import java.util.Map;

public interface CrewDAO {

    List<User> crewMembers(String crewName) throws DAOException;
    boolean createCrew(String crewName, Map<String, User> users) throws DAOException;
    boolean doesCrewNameExist(String crewName) throws DAOException;
    void deleteCrew(String crewName) throws DAOException;
    void deleteCrewMember(String crewName, User user) throws DAOException;
}
