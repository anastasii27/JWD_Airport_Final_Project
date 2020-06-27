package by.epam.tr.service;

import by.epam.tr.bean.Plane;
import java.time.LocalDate;
import java.util.List;

public interface PlaneService {
    List<Plane> freePlanes(String airportName, LocalDate departureDate, LocalDate destinationDate) throws ServiceException;
}
