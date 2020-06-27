package by.epam.tr.dao;

import by.epam.tr.bean.Plane;
import java.time.LocalDate;
import java.util.List;

public interface PlaneDao {
    List<Plane> allPlanesFromAirport(String airportName, LocalDate destinationDate) throws DAOException;
    List<Plane> takenOnFlightPlanes(String airportName, LocalDate departureDate) throws DAOException;
}
