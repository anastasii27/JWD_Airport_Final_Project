package by.epam.tr.service.impl;

import by.epam.tr.bean.Group;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.dao.GroupDAO;
import by.epam.tr.service.GroupService;
import by.epam.tr.service.ServiceException;
import java.util.ArrayList;

public class GroupServiceImpl implements GroupService {

    @Override
    public ArrayList<Group> userGroups(String login) throws ServiceException {

        GroupDAO dao = DAOFactory.getInstance().getGroupDAO();
        ArrayList <Group> groups;

        try {
            groups = dao.userGroups(login);

            if(groups.size()==0){
                return null;
            }

        } catch (DAOException e) {
            throw new ServiceException("Exception during user`s group getting!");
        }
        return groups;
    }
}
