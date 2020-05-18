package by.epam.tr.dao;

import by.epam.tr.bean.Group;
import java.util.ArrayList;

public interface GroupDAO {

    ArrayList<Group> userGroups(String login) throws DAOException;
}
