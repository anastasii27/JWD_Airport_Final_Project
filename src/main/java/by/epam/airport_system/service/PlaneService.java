package by.epam.airport_system.service;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.Plane;
import java.util.List;

public interface PlaneService {
    List<Plane> freePlanesForFlight(Flight flight) throws ServiceException;
}

