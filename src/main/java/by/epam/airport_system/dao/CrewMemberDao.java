package by.epam.airport_system.dao;

import by.epam.airport_system.bean.User;
import java.util.List;

public interface CrewMemberDao {
    List<User> crewMembers(String crewName) throws DaoException;
    int deleteCrewMember(String crewName, User user) throws DaoException;
    int addCrewMember(String crewName, List<User> crewMembers) throws DaoException;
    boolean isUserInTheCrew(String crewName, User user) throws DaoException;
}
