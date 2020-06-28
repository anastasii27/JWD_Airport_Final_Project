package by.epam.tr.service;

import by.epam.tr.bean.Plane;
import java.util.List;

public interface PlaneService {
    List<Plane> freePlanesAtAirport(String airportName) throws ServiceException;
}

