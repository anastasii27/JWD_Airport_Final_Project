package by.epam.tr.service;

import by.epam.tr.bean.User;
import java.util.List;

public interface CrewMemberService {

    List<User> crewMembers(String crewName) throws ServiceException;
    boolean deleteCrewMember(String crewName, User user) throws ServiceException;
    boolean addCrewMember(String crewName, List<User> crewMembers) throws ServiceException;
}
