package by.epam.tr.dao;

import by.epam.tr.bean.User;
import java.util.List;

public interface CrewDAO {

    List<User> crewMembers(String crewName) throws DAOException;
}
