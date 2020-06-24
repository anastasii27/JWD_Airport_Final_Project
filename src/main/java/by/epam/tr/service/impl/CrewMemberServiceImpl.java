package by.epam.tr.service.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.CrewMemberDAO;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.service.CrewMemberService;
import by.epam.tr.service.ServiceException;
import java.util.List;

public class CrewMemberServiceImpl implements CrewMemberService {

    private CrewMemberDAO dao = DAOFactory.getInstance().getCrewMemberDAO();
    private int changedRowsAmount;

    @Override
    public List<User> crewMembers(String crewName) throws ServiceException {
        List<User> crew;
        try {
            crew =dao.crewMembers(crewName);
        } catch (DAOException e) {
            throw new ServiceException("Exception during crew members getting", e);
        }
        return crew;
    }

    @Override
    public boolean deleteCrewMember(String crewName, User user) throws ServiceException {
        try {
            if(user!= null && dao.isUserInTheCrew(crewName, user)){
                changedRowsAmount = dao.deleteCrewMember(crewName, user);
            }
        } catch (DAOException e) {
            throw new ServiceException("Exception during crew member deleting", e);
        }
        return changedRowsAmount !=0;
    }

    @Override
    public boolean addCrewMember(String crewName, List<User> crewMembers) throws ServiceException {
        try {
            for (User user: crewMembers) {
                if(user == null || dao.isUserInTheCrew(crewName, user)){
                    return false;
                }
            }
            changedRowsAmount = dao.addCrewMember(crewName, crewMembers);
        } catch (DAOException e) {
            throw new ServiceException("Exception during crew member adding", e);
        }
        return changedRowsAmount !=0;
    }
}
