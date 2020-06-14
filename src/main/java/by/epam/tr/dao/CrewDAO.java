package by.epam.tr.dao;

import by.epam.tr.bean.User;
import java.util.List;

public interface CrewDAO {

    List<User> crewMembers(String crewName) throws DAOException;
    boolean createCrew(String crewName) throws DAOException;
    boolean addMemberToCrew(User user) throws DAOException;
}
