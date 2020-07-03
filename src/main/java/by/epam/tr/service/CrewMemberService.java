package by.epam.tr.service;

import by.epam.tr.bean.User;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.Set;

public interface CrewMemberService {
    List<User> crewMembers(String crewName) throws ServiceException;
    boolean deleteCrewMember(String crewName, User user) throws ServiceException;
    boolean addCrewMember(String crewName, List<User> crewMembers) throws ServiceException;
    Multimap<String, User> allMembersOfCrews(Set<String> crews) throws ServiceException;
}
