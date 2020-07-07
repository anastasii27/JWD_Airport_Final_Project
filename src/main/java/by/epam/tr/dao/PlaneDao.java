package by.epam.tr.dao;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.Plane;
import java.time.LocalDate;
import java.util.List;

public interface PlaneDao {
    List<Plane> arrivedToAirportPlane(String airportName, LocalDate date) throws DaoException;
    List<Plane> takenOnFlightPlanes(String airportName, LocalDate date) throws DaoException;
    List<Plane> allPlanes() throws DaoException;
    Flight firstPlaneFlightAfterDate( String planeNumber, LocalDate date) throws DaoException;
}
