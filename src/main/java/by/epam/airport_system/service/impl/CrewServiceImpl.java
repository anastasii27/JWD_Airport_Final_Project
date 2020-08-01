package by.epam.airport_system.service.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.User;
import by.epam.airport_system.dao.*;
import by.epam.airport_system.service.CrewService;
import by.epam.airport_system.service.ServiceException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class CrewServiceImpl implements CrewService {
    private final static int HOURS_BETWEEN_FLIGHTS = 3;
    private final static String HOME_AIRPORT = "MSQ";
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
    public boolean doesCrewExist(String crewName) throws ServiceException {
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
    public List<String> crewsList() throws ServiceException {
        try {
            return dao.allCrews();
        } catch (DaoException e) {
            throw new ServiceException("Exception during all crews getting", e);
        }
    }

    @Override
    public Set<String> findFreeCrewsForFlight(Flight flight) throws ServiceException {
        List<String> crews = new ArrayList<>();

        try {
            if(flight != null){
                crews = crewsAtAirport(flight.getDepartureAirportShortName());
            }

            return findFreeCrews(crews, flight);
        } catch (DaoException e) {
            throw new ServiceException("Exception during free crews searching", e);
        }
    }

    private List<String> crewsAtAirport(String airportShortName) throws DaoException {
        if(airportShortName.equals(HOME_AIRPORT)) {
            return dao.allCrews();
        }else {
            return dao.takenOnFlightsCrews();
        }
    }

    private Set<String> findFreeCrews(List<String> crews, Flight flight) throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        Set<String> freeCrews = new HashSet<>();
        List <User> crewMembers;

        for (String crewName: crews) {
            crewMembers = crewMemberDao.crewMembers(crewName);

            if(areAllCrewMembersFree(crewMembers, flight)){
                freeCrews.add(crewName);
            }
        }
        return freeCrews;
    }

    private boolean areAllCrewMembersFree(List<User> crewMembers, Flight newFlight) throws DaoException {
        for(User crewMember:crewMembers){
            Flight lastFlight = crewMemberLastFlight(crewMember, newFlight);
            Flight nextUserFlightAfterNewOne = crewMemberNextFlight(crewMember, newFlight);

             if(!isCrewMemberFree(newFlight, lastFlight, nextUserFlightAfterNewOne)){
                return  false;
            }
        }
        return true;
    }

    private boolean isCrewMemberFree(Flight newFlight, Flight lastFlight, Flight nextFlight){
        return  isNextFlightAfterNew(newFlight, nextFlight)
                    && isLastFlightBeforeNew(newFlight, lastFlight);
    }

    private Flight crewMemberLastFlight(User user, Flight newFlight) throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        LocalDate departureDate = newFlight.getDepartureDate();

        return userFlightsDao.lastUserFlightBeforeDate(user, departureDate);
    }

    private Flight crewMemberNextFlight(User user, Flight newFlight) throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        LocalDate destinationDate = newFlight.getDestinationDate();

        return userFlightsDao.firstUserFlightAfterDate(user, destinationDate);
    }

    private boolean isNextFlightAfterNew(Flight newFlight, Flight nextFlight){
        if(nextFlight == null){
            return true;
        }

        LocalDateTime newFlightArrival = LocalDateTime.of(newFlight.getDestinationDate(), newFlight.getDestinationTime());
        LocalDateTime nextFlightDeparture = LocalDateTime.of(nextFlight.getDepartureDate(), nextFlight.getDepartureTime());

        if(newFlight.getDestinationAirportShortName().equals(nextFlight.getDepartureAirportShortName())) {
            return nextFlightDeparture.minusHours(HOURS_BETWEEN_FLIGHTS).isAfter(newFlightArrival);
        }
        return false;
    }

    private boolean isLastFlightBeforeNew(Flight newFlight, Flight lastFlight){
        if(lastFlight == null){
            return true;
        }

        LocalDateTime newFlightDeparture = LocalDateTime.of(newFlight.getDepartureDate(), newFlight.getDepartureTime());
        LocalDateTime lastFlightArrival = LocalDateTime.of(lastFlight.getDestinationDate(), lastFlight.getDestinationTime());

        if(newFlight.getDepartureAirportShortName().equals(lastFlight.getDestinationAirportShortName())){
            return lastFlightArrival.plusHours(HOURS_BETWEEN_FLIGHTS).isBefore(newFlightDeparture);
        }
        return false;
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
