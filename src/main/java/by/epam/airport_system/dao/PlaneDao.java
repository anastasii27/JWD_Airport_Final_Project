package by.epam.airport_system.dao;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.Plane;
import java.time.LocalDate;
import java.util.List;

public interface PlaneDao {
    List<Plane> arrivedToAirportPlanes(String airportName, LocalDate date) throws DaoException;
    List<Plane> takenOnFlightPlanes(String airportName, LocalDate date) throws DaoException;
    List<Plane> allPlanes() throws DaoException;
    Flight firstPlaneFlightAfterDate( String planeNumber, LocalDate date) throws DaoException;
}
