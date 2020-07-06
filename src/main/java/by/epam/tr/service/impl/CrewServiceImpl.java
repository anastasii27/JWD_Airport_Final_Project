package by.epam.tr.service.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.User;
import by.epam.tr.dao.*;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class CrewServiceImpl implements CrewService {
    private final static int TIME_BETWEEN_FLIGHTS_FROM_ONE_AIRPORT = 3;
    private final static int TIME_BETWEEN_FLIGHTS_FROM_DIFFERENT_AIRPORTS = 23;
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
            Flight lastFlight = findCrewMemberLastFlight(crewMember, newFlight.getDepartureDate());
            Flight nextUserFlightAfterNewOne = findCrewMemberNextFlight(crewMember, newFlight.getDestinationDate());

             if(!isCrewMemberFree(newFlight, lastFlight, nextUserFlightAfterNewOne)){
                return  false;
            }
        }
        return true;
    }

    private Flight findCrewMemberLastFlight(User user, LocalDate newFlightDepartureDate) throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        return userFlightsDao.lastUserFlightBeforeDate(user, newFlightDepartureDate);
    }

    private Flight findCrewMemberNextFlight(User user,LocalDate newFlightDepartureDate) throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        return userFlightsDao.firstUserFlightAfterDate(user, newFlightDepartureDate);
    }

    private boolean isCrewMemberFree(Flight newUserFlight, Flight lastUserFlight, Flight nextUserFlightAfterNewOne){
        if(!isSecondFlightAfterFirstOne(newUserFlight, nextUserFlightAfterNewOne)){
            return false;
        }

        if(lastUserFlight == null){
            return false;
        }

        if(!newUserFlight.getDepartureAirportShortName().equals(lastUserFlight.getDestinationAirportShortName())){
            return false;
        }else if(!isSecondFlightBeforeFirstOne(newUserFlight, lastUserFlight)){
            return false;
        }

        return true;
    }

    private boolean isSecondFlightAfterFirstOne(Flight firstFlight, Flight secondFlight){
        if(secondFlight == null){
            return true;
        }
        LocalDateTime firstFlightArrival = LocalDateTime.of(firstFlight.getDestinationDate(), firstFlight.getDestinationTime());
        LocalDateTime secondFlightDeparture = LocalDateTime.of(secondFlight.getDepartureDate(), secondFlight.getDepartureTime());

        if(firstFlight.getDestinationAirportShortName().equals(secondFlight.getDepartureAirportShortName())) {
            return secondFlightDeparture.minusHours(TIME_BETWEEN_FLIGHTS_FROM_ONE_AIRPORT).isAfter(firstFlightArrival);
        }else {
            return secondFlightDeparture.minusHours(TIME_BETWEEN_FLIGHTS_FROM_DIFFERENT_AIRPORTS).isAfter(firstFlightArrival);
        }
    }

    private boolean isSecondFlightBeforeFirstOne(Flight firstFlight, Flight secondFlight){
        LocalDateTime firstFlightDeparture = LocalDateTime.of(firstFlight.getDepartureDate(), firstFlight.getDepartureTime());
        LocalDateTime secondFlightArrival = LocalDateTime.of(secondFlight.getDestinationDate(), secondFlight.getDestinationTime());

        return secondFlightArrival.plusHours(TIME_BETWEEN_FLIGHTS_FROM_ONE_AIRPORT).isBefore(firstFlightDeparture) ;
    }

    @Override
    public boolean setCrewForFlight(String crewName, String flightNumber) throws ServiceException {
        try {
            return dao.setCrewForFlight(crewName, flightNumber) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Exception during crew to flight setting", e);
        }
    }

    @Override
    public String flightCrew(Flight flight) throws ServiceException {
        try {
            return dao.flightCrew(flight);
        } catch (DaoException e) {
            throw new ServiceException("Exception during flight crew searching", e);
        }
    }
}
