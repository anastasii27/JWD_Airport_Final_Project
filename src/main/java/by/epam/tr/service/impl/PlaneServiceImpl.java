package by.epam.tr.service.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.Plane;
import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.DaoFactory;
import by.epam.tr.dao.PlaneDao;
import by.epam.tr.service.PlaneService;
import by.epam.tr.service.ServiceException;
import java.time.LocalDate;
import java.util.List;

public class PlaneServiceImpl implements PlaneService {
    private final static String HOME_AIRPORT = "MSQ";
    private PlaneDao dao = DaoFactory.getInstance().getPlaneDao();

    public List<Plane> freePlanesForFlight(Flight flight) throws ServiceException {
        String airportName = flight.getDepartureAirportShortName();
        LocalDate date = flight.getDepartureDate();

        try {
            if(airportName.equals(HOME_AIRPORT)){
                return findFreePlanesForHomeAirport(airportName, date);
            }else{
                return findFreePlanesForAirport(airportName, date);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception during free planes searching!", e);
        }
    }

    private List<Plane> findFreePlanesForHomeAirport(String airportName, LocalDate date) throws DaoException {
        List<Plane> allPlanes = dao.allPlanes();
        List<Plane> planesInAirportNow = dao.arrivedToAirportPlane(airportName, date);

        removeTakenOnFlightPlanesFromList(allPlanes, airportName, date);
        allPlanes.addAll(planesInAirportNow);

        return allPlanes;
    }

    private List<Plane> findFreePlanesForAirport(String airportName, LocalDate date) throws DaoException {
        List<Plane> planesInAirport = dao.arrivedToAirportPlane(airportName, date);
        removeTakenOnFlightPlanesFromList(planesInAirport, airportName, date);

        return planesInAirport;
    }

    private void removeTakenOnFlightPlanesFromList(List<Plane> planes, String airportName, LocalDate date) throws DaoException {
        List<Plane> takenOnFlightPlanes = dao.takenOnFlightPlanes(airportName, date);
        planes.removeAll(takenOnFlightPlanes);
    }
}
