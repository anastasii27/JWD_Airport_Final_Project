package by.epam.tr.service.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.User;
import by.epam.tr.dao.*;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import java.time.LocalDateTime;
import java.util.*;

public class CrewServiceImpl implements CrewService {
    private CrewDao dao = DaoFactory.getInstance().getCrewDAO();

    @Override
    public boolean createCrew(String crewName, Map<String, User> users) throws ServiceException {
        try {
            return dao.createCrew(crewName, users);
        } catch (DaoException e) {
            throw new ServiceException("Exception during crew creation", e);
        }
    }

    @Override
    public boolean doesCrewNameExist(String crewName) throws ServiceException {
        try {
            return dao.doesCrewNameExist(crewName);
        } catch (DaoException e) {
            throw new ServiceException("Exception during crew existence checking", e);
        }
    }

    @Override
    public boolean deleteCrew(String crewName) throws ServiceException {
        try {
            return dao.deleteCrew(crewName) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Exception during crew deleting", e);
        }
    }

    @Override
    public User findMainPilot(String crewName) throws ServiceException {
        try {
            return dao.findMainPilot(crewName);
        } catch (DaoException e) {
            throw new ServiceException("Exception during main pilot searching", e);
        }
    }

    @Override
    public List<String> allCrews() throws ServiceException {
        try {
            return dao.allCrews();
        } catch (DaoException e) {
            throw new ServiceException("Exception during main pilot searching", e);
        }
    }

    @Override
    public Set<String> findFreeCrewsForFlight(Flight flight) throws ServiceException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        Set<String> freeCrews = new HashSet<>();

        try {
            List<String> allCrews = dao.allCrews();
            List <User> crewMembers;

            for (String crewName: allCrews) {
                crewMembers = crewMemberDao.crewMembers(crewName);
                if(areAllCrewMembersFree(crewMembers, flight)){
                    freeCrews.add(crewName);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception during free crews searching", e);
        }
        return freeCrews;
    }

    private boolean areAllCrewMembersFree(List<User> crewMembers, Flight newFlight) throws DaoException {
        for(User crewMember:crewMembers){
            Flight lastFlight = findCrewMemberLastFlight(crewMember);
            if(lastFlight == null){
                return false;
            }else if(!isCrewMemberFree(newFlight, lastFlight)){
                return  false;
            }
        }
        return true;
    }

    private Flight findCrewMemberLastFlight(User user) throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        return userFlightsDao.lastUserFlight(user);
    }

    private boolean isCrewMemberFree(Flight newUserFlight, Flight lastUserFlight){
        if(newUserFlight.getDepartureAirportShortName().equals(lastUserFlight.getDestinationAirportShortName())){
            if(isLastFlightBeforeNewOne(newUserFlight, lastUserFlight)){
                return true;
            }
        }
        return false;
    }

    private boolean isLastFlightBeforeNewOne(Flight newUserFlight, Flight lastUserFlight){
        LocalDateTime lastFlightArrival = LocalDateTime.of(lastUserFlight.getDestinationDate(), lastUserFlight.getDestinationTime());
        LocalDateTime newFlightDeparture = LocalDateTime.of(newUserFlight.getDepartureDate(), newUserFlight.getDepartureTime());

        return lastFlightArrival.plusHours(3).isBefore(newFlightDeparture) ;
    }
}
