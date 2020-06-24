package by.epam.tr.dao;

import by.epam.tr.bean.User;
import java.util.List;

public interface CrewMemberDAO {

    List<User> crewMembers(String crewName) throws DAOException;
    int deleteCrewMember(String crewName, User user) throws DAOException;
    int addCrewMember(String crewName, List<User> crewMembers) throws DAOException;
    boolean isUserInTheCrew(String crewName, User user) throws DAOException;
}
