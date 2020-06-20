package by.epam.tr.dao;

import by.epam.tr.bean.User;
import java.util.List;

public interface CrewMemberDAO {

    List<User> crewMembers(String crewName) throws DAOException;
    int deleteCrewMember(String crewName, User user) throws DAOException;
    int addCrewMember(String crewName, User user) throws DAOException;
}
