package by.epam.airport_system.service.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.dao.CrewMemberDao;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.service.CrewMemberService;
import by.epam.airport_system.service.ServiceException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Set;

public class CrewMemberServiceImpl implements CrewMemberService {
    private CrewMemberDao dao = DaoFactory.getInstance().getCrewMemberDAO();

    @Override
    public List<User> crewMembers(String crewName) throws ServiceException {
        try {
            return dao.crewMembers(crewName);
        } catch (DaoException e) {
            throw new ServiceException("Exception during crew members getting", e);
        }
    }

    @Override
    public boolean deleteCrewMember(String crewName, User user) throws ServiceException {
        try {
            if(user == null || !dao.isUserInTheCrew(crewName, user)){
                return false;
            }
            if(isItMainPilot(user, crewName)){
                dao.deleteCrewMainPilot(crewName);
            }

            return dao.deleteCrewMember(crewName, user) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Exception during crew member deleting", e);
        }
    }

    private boolean isItMainPilot(User user, String crewName) throws ServiceException {
        User mainPilot = findCrewMainPilot(crewName);
        if(mainPilot == null){
            return false;
        }

        return user.getName().equals(mainPilot.getName())
                    && user.getSurname().equals(mainPilot.getSurname());
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
    public Multimap<String, User> crewsMembers(Set<String> crews) throws ServiceException {
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
        List<User> crewMembers = dao.crewMembers(crewName);

        for (User user: crewMembers){
            crewsWithMembers.put(crewName, user);
        }
    }

    @Override
    public User findCrewMainPilot(String crewName) throws ServiceException {
        try {
            return dao.findMainPilot(crewName);
        } catch (DaoException e) {
            throw new ServiceException("Exception during main pilot searching", e);
        }
    }
}
