package by.epam.tr.service.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.CrewMemberDao;
import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.DaoFactory;
import by.epam.tr.service.CrewMemberService;
import by.epam.tr.service.ServiceException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Set;

public class CrewMemberServiceImpl implements CrewMemberService {
    private CrewMemberDao dao = DaoFactory.getInstance().getCrewMemberDAO();

    @Override
    public List<User> crewMembers(String crewName) throws ServiceException {
        try {
            return findAllCrewMembers(crewName);
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

    @Override
    public Multimap<String, User> allMembersOfCrews(Set<String> crews) throws ServiceException {
        Multimap<String, User> crewsWithMembers = ArrayListMultimap.create();

        try {
            for(String crewName: crews) {
                putMembersToMap(crewName, crewsWithMembers);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception during crews members getting", e);
        }
        return crewsWithMembers;
    }

    private void putMembersToMap(String crewName, Multimap<String, User> crewsWithMembers) throws DaoException {
        List<User> crewMembers = findAllCrewMembers(crewName);

        for (User user: crewMembers){
            crewsWithMembers.put(crewName, user);
        }
    }

    private List<User> findAllCrewMembers(String crewName) throws DaoException {
        return dao.crewMembers(crewName);
    }
}
