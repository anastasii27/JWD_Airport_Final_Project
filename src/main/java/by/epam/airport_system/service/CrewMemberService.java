package by.epam.airport_system.service;

import by.epam.airport_system.bean.User;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Set;

public interface CrewMemberService {
    List<User> crewMembers(String crewName) throws ServiceException;
    boolean deleteCrewMember(String crewName, User user) throws ServiceException;
    boolean addCrewMember(String crewName, List<User> crewMembers) throws ServiceException;
    Multimap<String, User> crewsMembers(Set<String> crews) throws ServiceException;
    User findCrewMainPilot(String crewName) throws ServiceException;
}
