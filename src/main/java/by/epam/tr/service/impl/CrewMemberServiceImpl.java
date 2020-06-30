package by.epam.tr.service.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.CrewMemberDao;
import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.DaoFactory;
import by.epam.tr.service.CrewMemberService;
import by.epam.tr.service.ServiceException;
import java.util.List;

public class CrewMemberServiceImpl implements CrewMemberService {
    private CrewMemberDao dao = DaoFactory.getInstance().getCrewMemberDAO();

    @Override
    public List<User> crewMembers(String crewName) throws ServiceException {
        try {
            return  dao.crewMembers(crewName);
        } catch (DaoException e) {
            throw new ServiceException("Exception during crew members getting", e);
        }
    }

    @Override
    public boolean deleteCrewMember(String crewName, User user) throws ServiceException {
        int changedRowsAmount = 0;
        try {
            if(user!= null && dao.isUserInTheCrew(crewName, user)){
                changedRowsAmount = dao.deleteCrewMember(crewName, user);
            }
        } catch (DaoException e) {
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
            return dao.addCrewMember(crewName, crewMembers) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Exception during crew member adding", e);
        }
    }
}
