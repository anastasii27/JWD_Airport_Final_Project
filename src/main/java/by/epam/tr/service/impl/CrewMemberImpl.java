package by.epam.tr.service.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.CrewMemberDAO;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.dao.UserDAO;
import by.epam.tr.service.CrewMemberService;
import by.epam.tr.service.ServiceException;
import java.util.List;

public class CrewMemberImpl implements CrewMemberService {

    private CrewMemberDAO dao = DAOFactory.getInstance().getCrewMemberDAO();
    private int changedRowsAmount;

    @Override
    public List<User> crewMembers(String crewName) throws ServiceException {

        List<User> crew;
        try {
            crew =dao.crewMembers(crewName);

        } catch (DAOException e) {
            throw new ServiceException("Exception during crew members getting");
        }
        return crew;
    }

    @Override
    public boolean deleteCrewMember(String crewName, User user) throws ServiceException {

        try {
            changedRowsAmount = dao.deleteCrewMember(crewName, user);

        } catch (DAOException e) {
            throw new ServiceException("Exception during crew member deleting");
        }

        return changedRowsAmount !=0;
    }

    @Override
    public boolean addCrewMember(String crewName, User user) throws ServiceException {

        try {
            changedRowsAmount = dao.addCrewMember(crewName, user);

        } catch (DAOException e) {
            throw new ServiceException("Exception during crew member adding");
        }

        return changedRowsAmount !=0;
    }

    @Override
    public List<User> beyondCrewMembers(String crewName) throws ServiceException {

        UserDAO dao = DAOFactory.getInstance().getUserDAO();
        List<User> crewMembers = crewMembers(crewName);
        List<User> allUsers;
        try {

            allUsers = dao.allUsers();
            allUsers.removeAll(crewMembers);

        } catch (DAOException e) {
            throw new ServiceException("Exception during all users getting");
        }
        return allUsers;
    }
}