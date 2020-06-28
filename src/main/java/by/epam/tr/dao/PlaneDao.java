package by.epam.tr.dao;

import by.epam.tr.bean.Plane;
import java.util.List;

public interface PlaneDao {
    List<Plane> allPlanesFromAirport(String airportName) throws DAOException;
    List<Plane> takenOnFlightPlanes(String airportName) throws DAOException;
    List<Plane> allPlanes() throws DAOException;
}
