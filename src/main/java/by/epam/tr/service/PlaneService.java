package by.epam.tr.service;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.Plane;
import java.util.List;

public interface PlaneService {
    List<Plane> freePlanesForFlight(Flight flight) throws ServiceException;
}

