package by.epam.airport_system.dao.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.Plane;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.dao.H2DataBaseCreation;
import by.epam.airport_system.dao.PlaneDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaneDaoImplTest extends H2DataBaseCreation {
    private final static String EXISTING_AIRPORT_1 = "MSQ1";
    private final static String EXISTING_AIRPORT_2 = "MSQ2";
    private final static String NOT_EXISTING_AIRPORT = "MSQ0";
    private Plane plane1;
    private Plane plane2;
    private LocalDate date;

    @Before
    public void setUp(){
        plane1 = new Plane("Airbus 123", "A-2772");
        plane2 = new Plane("Airbus 828", "A-8281");
        date = LocalDate.of(2020, 7, 26);
    }

    @Test
    public void arrivedToAirportPlane_whenAirportIsNull_thenEmptyList() throws DaoException {
        PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao();
        List<Plane> actual = planeDao.arrivedToAirportPlane(null, LocalDate.now());

        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test(expected = NullPointerException.class)
    public void arrivedToAirportPlane_whenDateIsNull_thenNullPointerException() throws DaoException {
        PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao();
        planeDao.arrivedToAirportPlane(EXISTING_AIRPORT_1, null);
    }

    @Test
    public void arrivedToAirportPlane_whenAirportDoesNotExist_thenEmptyList() throws DaoException {
        PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao();
        List<Plane> actual = planeDao.arrivedToAirportPlane(NOT_EXISTING_AIRPORT, date);

        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void arrivedToAirportPlane_whenAirportExists_thenList() throws DaoException {
        PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao();
        List<Plane> actual = planeDao.arrivedToAirportPlane(EXISTING_AIRPORT_2,  date.plusDays(1));
        List<Plane> expected = new ArrayList<Plane>(){{
            add(plane1);
        }};

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void takenOnFlightPlanes_whenAirportIsNull_thenEmptyList() throws DaoException {
        PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao();
        List<Plane> actual = planeDao.takenOnFlightPlanes(null, date);

        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test(expected = NullPointerException.class)
    public void takenOnFlightPlanes_whenDateIsNull_thenNullPointerException() throws DaoException {
        PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao();
        planeDao.takenOnFlightPlanes(NOT_EXISTING_AIRPORT, null);
    }

    @Test
    public void takenOnFlightPlanes_whenAirportDoesNotExist_thenEmptyList() throws DaoException {
        PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao();
        List<Plane> actual = planeDao.takenOnFlightPlanes(NOT_EXISTING_AIRPORT, date);

        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void takenOnFlightPlanes_whenAirportExists_thenList() throws DaoException {
        PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao();
        List<Plane> actual = planeDao.takenOnFlightPlanes(EXISTING_AIRPORT_1, date.plusDays(1));
        List<Plane> expected = new ArrayList<Plane>(){{
            add(plane1);
        }};

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void allPlanes() throws DaoException {
        PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao();
        List<Plane> actual = planeDao.allPlanes();
        List<Plane> expected = new ArrayList<Plane>(){{
            add(plane1);
            add(plane2);
        }};

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void firstPlaneFlightAfterDate_whenPlaneNumberIsNull_thenNull() throws DaoException {
        PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao();
        Flight actual = planeDao.firstPlaneFlightAfterDate(null, date);

        Assert.assertNull(actual);
    }

    @Test(expected = NullPointerException.class)
    public void firstPlaneFlightAfterDate_whenDateIsNull_thenNullPointerException() throws DaoException {
        PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao();
        planeDao.firstPlaneFlightAfterDate(EXISTING_AIRPORT_1, null);
    }

    @Test
    public void firstPlaneFlightAfterDate_whenPlaneNumberExists_thenFlight() throws DaoException {
        PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao();
        Flight actual = planeDao.firstPlaneFlightAfterDate(plane1.getNumber(), date.minusDays(1));
        Flight expected = Flight.builder().departureDate(date)
                                            .departureTime(LocalTime.of(19,53))
                                            .departureAirportShortName(EXISTING_AIRPORT_1).build();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void firstPlaneFlightAfterDate_whenPlaneNumberDoesNotExist_thenNull() throws DaoException {
        PlaneDao planeDao = DaoFactory.getInstance().getPlaneDao();
        Flight actual = planeDao.firstPlaneFlightAfterDate(EXISTING_AIRPORT_1, LocalDate.now());

        Assert.assertNull(actual);
    }
}