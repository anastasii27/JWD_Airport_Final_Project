package by.epam.tr.service;

import by.epam.tr.bean.Group;
import java.util.ArrayList;

public interface GroupService {

    ArrayList<Group> userGroups(String login) throws ServiceException;
}
