package by.epam.airport_system.service.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.Plane;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.dao.PlaneDao;
import by.epam.airport_system.service.PlaneService;
import by.epam.airport_system.service.ServiceException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlaneServiceImpl implements PlaneService {
    private final static String HOME_AIRPORT = "MSQ";
    private final static int HOURS_BETWEEN_FLIGHT_FROM_ONE_AIRPORT = 4;
    private final static int HOURS_BETWEEN_FLIGHT_FROM_DIFFERENT_AIRPORTS = 23;
    private PlaneDao dao = DaoFactory.getInstance().getPlaneDao();

    public List<Plane> freePlanesForFlight(Flight flight) throws ServiceException {
        String airportName = flight.getDepartureAirportShortName();

        try {
            if(airportName.equals(HOME_AIRPORT)){
                return freePlanesForHomeAirport(flight);
            }else{
                return freePlanesForTransborderAirport(flight);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception during free planes searching!", e);
        }
    }

    private List<Plane> freePlanesForHomeAirport(Flight newFlight) throws DaoException {
        String airportName = newFlight.getDepartureAirportShortName();
        LocalDate date = newFlight.getDepartureDate();
        List<Plane> allPlanes = dao.allPlanes();

        removeTakenOnFlightPlanesFromList(allPlanes, airportName, date);
        addArrivedToAirportPlanesToList(allPlanes, airportName, date);
        removeUnsuitablePlanesFromList(allPlanes, newFlight);

        return allPlanes;
    }

    private List<Plane> freePlanesForTransborderAirport(Flight newFlight) throws DaoException {
        String airportName = newFlight.getDepartureAirportShortName();
        LocalDate date = newFlight.getDepartureDate();
        List<Plane> planesInAirport = dao.arrivedToAirportPlanes(airportName, date);

        removeTakenOnFlightPlanesFromList(planesInAirport, airportName, date);
        removeUnsuitablePlanesFromList(planesInAirport, newFlight);

        return planesInAirport;
    }

    private void addArrivedToAirportPlanesToList(List<Plane> planes, String airportName, LocalDate date) throws DaoException {
        List<Plane> planesInAirportNow = dao.arrivedToAirportPlanes(airportName, date);
        planes.addAll(planesInAirportNow);
    }

    private void removeTakenOnFlightPlanesFromList(List<Plane> planes, String airportName, LocalDate date) throws DaoException {
        List<Plane> takenOnFlightPlanes = dao.takenOnFlightPlanes(airportName, date);
        planes.removeAll(takenOnFlightPlanes);
    }

    private void removeUnsuitablePlanesFromList(List<Plane> planes, Flight newFlight) throws DaoException {
        List<Plane> unsuitablePlanes = unsuitablePlanesForFlight(planes, newFlight);
        planes.removeAll(unsuitablePlanes);
    }

    private List<Plane> unsuitablePlanesForFlight(List<Plane> planes, Flight newFlight) throws DaoException {
        List<Plane> unsuitablePlanes = new ArrayList<>();
        LocalDate newFlightDepartureDate = newFlight.getDepartureDate();

        for (Plane plane: planes){
            Flight planeNextFlight = dao.firstPlaneFlightAfterDate(plane.getNumber(), newFlightDepartureDate);
            if(planeNextFlight == null){
                continue;
            }

            if(!doesPlaneSuitForFlight(newFlight, planeNextFlight)){
                unsuitablePlanes.add(plane);
            }
        }
        return unsuitablePlanes;
    }

    private boolean doesPlaneSuitForFlight(Flight newPlaneFlight, Flight planeNextFlight){
        LocalDateTime newFlightDate = LocalDateTime.of(newPlaneFlight.getDestinationDate(), newPlaneFlight.getDestinationTime());
        LocalDateTime nextFlightDate = LocalDateTime.of(planeNextFlight.getDepartureDate(), planeNextFlight.getDepartureTime());

        if(newPlaneFlight.getDestinationAirportShortName().equals(planeNextFlight.getDepartureAirportShortName())){
            return newFlightDate.plusHours(HOURS_BETWEEN_FLIGHT_FROM_ONE_AIRPORT).isBefore(nextFlightDate);
        }else {
            return newFlightDate.plusHours(HOURS_BETWEEN_FLIGHT_FROM_DIFFERENT_AIRPORTS).isBefore(nextFlightDate);
        }
    }
}

