package by.epam.tr.service.impl;

import by.epam.tr.bean.Plane;
import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.DaoFactory;
import by.epam.tr.dao.PlaneDao;
import by.epam.tr.service.PlaneService;
import by.epam.tr.service.ServiceException;
import java.util.ArrayList;
import java.util.List;

public class PlaneServiceImpl implements PlaneService {
    private final static String MINSK_NATIONAL_AIRPORT = "MSQ";
    private PlaneDao dao = DaoFactory.getInstance().getPlaneDao();

    @Override
    public List<Plane> freePlanesAtAirport(String airportName) throws ServiceException {
        List<Plane> allPlanes;

        try {
            if(airportName.equals(MINSK_NATIONAL_AIRPORT)){
                allPlanes = dao.allPlanes();
            }else{
                allPlanes  = dao.allPlanesFromAirport(airportName);
            }
            List<Plane> takenOnFlightPlanes = dao.takenOnFlightPlanes(airportName);

            return findFreePlanes(allPlanes, takenOnFlightPlanes);
        } catch (DaoException e) {
            throw new ServiceException("Exception during free planes searching!", e);
        }
    }

    private List<Plane> findFreePlanes(List<Plane> allPlanes, List<Plane> takenOnFlightPlanes ){
        List<Plane> inappropriatePlane = new ArrayList<>();

        for (Plane plane1: allPlanes) {
            for (Plane plane2: takenOnFlightPlanes) {
                if(plane1.getNumber().equals(plane2.getNumber())){
                    inappropriatePlane.add(plane1);
                }
            }
        }
        allPlanes.removeAll(inappropriatePlane);

        return allPlanes;
    }
}
