package by.epam.airport_system.dao.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.Plane;
import by.epam.airport_system.bean.User;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.dao.FlightDao;
import by.epam.airport_system.dao.H2DataBaseCreation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlightDaoImplTest extends H2DataBaseCreation{
    private final static String EXISTING_FLIGHT = "KL 2112";
    private final static String NOT_EXISTING_FLIGHT = "KL 0000";
    private final static String NOT_EXISTING_AIRPORT = "MSW";
    private final static String PLANE_MODEL = "Airbus 123";
    private final static String PLANE_NUMBER = "A-2772";
    private final static String CREW_NAME = "A1";
    private Flight flight;
    private User dispatcher;

    @Before
    public void setUp(){
        flight = Flight.builder().id(1)
                .flightNumber(EXISTING_FLIGHT)
                .status("Scheduled")
                .destinationDate(LocalDate.of(2020,  7,26))
                .destinationTime(LocalTime.of(20, 53))
                .destinationAirport("Minsk-2")
                .destinationCity("Minsk")
                .destinationCountry("Belarus")
                .destinationAirportShortName("MSQ2")
                .departureDate(LocalDate.of(2020,  7,26))
                .departureTime(LocalTime.of(19, 53))
                .departureAirport("Minsk-1")
                .departureCity("Minsk")
                .departureCountry("Belarus")
                .departureAirportShortName("MSQ1")
                .build();

        dispatcher = User.builder().name("Аня").surname("Корытько").build();
    }

    @Test
    public void airportArrivals_whenAirportIsNull_ThenEmptyList() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        List<Flight> actual = flightDao.airportArrivals(flight.getDepartureDate(), null);

        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void airportArrivals_whenAirportDoesNotExist_ThenEmptyList() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        List<Flight> actual = flightDao.airportArrivals(flight.getDepartureDate(), NOT_EXISTING_AIRPORT);

        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void airportArrivals_whenArrivalExists_ThenList() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        List<Flight> expected =  new ArrayList<Flight>(){{
            add(Flight.builder().status(flight.getStatus())
                                .planeModel(PLANE_MODEL)
                                .departureDate(flight.getDepartureDate())
                                .departureTime(flight.getDestinationTime())
                                .destinationCity(flight.getDestinationCity())
                                .destinationAirportShortName(flight.getDepartureAirportShortName())
                                .flightNumber(flight.getFlightNumber()).build());
        }};
        List<Flight> actual = flightDao.airportArrivals(flight.getDepartureDate(), flight.getDestinationAirportShortName());

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void airportArrivals_whenDateIsNull_ThenNullPointerException() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flightDao.airportArrivals(null, flight.getDepartureAirportShortName());
    }

    @Test
    public void flightInfo_whenFlightExists_thenFlight() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flight.setPlane(new Plane(PLANE_MODEL,PLANE_NUMBER));
        Flight actual = flightDao.flightInfo(EXISTING_FLIGHT, flight.getDepartureDate());

        Assert.assertEquals(flight, actual);
    }

    @Test
    public void flightInfo_whenFlightNumberIsNull_thenNull() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        Flight actual = flightDao.flightInfo(null, flight.getDepartureDate());

        Assert.assertNull(actual);
    }

    @Test(expected = NullPointerException.class)
    public void flightInfo_whenDateIsNull_thenNullPointerException() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flightDao.flightInfo(EXISTING_FLIGHT, null);
    }

    @Test
    public void flightInfo_whenFlightNumberDoesNotExist_thenNull() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        Flight actual = flightDao.flightInfo(NOT_EXISTING_FLIGHT, flight.getDepartureDate());

        Assert.assertNull(actual);
    }

    @Test
    public void createFlight_whenAllValuesAreValid_thenReturnOne() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flight.setPlaneNumber(PLANE_NUMBER);
        flight.setDispatcher(dispatcher);

        int expected =1;
        int actual = flightDao.createFlight(flight);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void createFlight_whenDateIsNull_thenNullPointerException() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flight.setDepartureDate(null);

        flightDao.createFlight(flight);
    }

    @Test(expected = NullPointerException.class)
    public void createFlight_whenTimeIsNull_thenNullPointerException() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flight.setDepartureTime(null);

        flightDao.createFlight(flight);
    }

    @Test(expected = NullPointerException.class)
    public void createFlight_whenDispatcherIsNull_thenNullPointerException() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flight.setDispatcher(null);

        flightDao.createFlight(flight);
    }

    @Test(expected = DaoException.class)
    public void createFlight_whenAirportIsNull_thenDaoException() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flight.setDepartureAirportShortName(null);
        flight.setDispatcher(dispatcher);

        flightDao.createFlight(flight);
    }

    @Test(expected = DaoException.class)
    public void createFlight_whenPlaneNumberIsNull_thenDaoException() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flight.setPlaneNumber(null);
        flight.setDispatcher(dispatcher);

        flightDao.createFlight(flight);
    }

    @Test
    public void doesFlightNumberExist_whenFlightNumberIsNull_thenFalse() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        boolean actual = flightDao.doesFlightNumberExist(null, LocalDate.now());

        Assert.assertFalse(actual);
    }

    @Test(expected = NullPointerException.class)
    public void doesFlightNumberExist_whenDateIsNull_thenNullPointerException() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flightDao.doesFlightNumberExist(EXISTING_FLIGHT, null);
    }

    @Test
    public void doesFlightNumberExist_whenFlightExists_thenTrue() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        boolean actual =flightDao.doesFlightNumberExist(EXISTING_FLIGHT, flight.getDepartureDate());

        Assert.assertTrue(actual);
    }

    @Test
    public void doesFlightNumberExist_whenFlightDoesNotExist_thenFalse() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        boolean actual =flightDao.doesFlightNumberExist(NOT_EXISTING_FLIGHT, flight.getDepartureDate());

        Assert.assertFalse(actual);
    }

    @Test(expected = NullPointerException.class)
    public void allFlightByDay_whenDateIsNull_thenNullPointerException() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flightDao.allFlightByDay(null);
    }

    @Test
    public void allFlightByDay_whenThereAreNoFlightsThisDay_thenEmptyList() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        List<Flight> actual = flightDao.allFlightByDay(LocalDate.now());

        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void allFlightByDay_whenFlightsExist_thenList() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        List<Flight> actual = flightDao.allFlightByDay(flight.getDepartureDate());
        List<Flight> expected =  new ArrayList<Flight>(){{
            add(Flight.builder()
                        .flightNumber(EXISTING_FLIGHT)
                        .status(flight.getStatus())
                        .planeModel(PLANE_MODEL)
                        .destinationDate(flight.getDestinationDate())
                        .destinationTime(flight.getDestinationTime())
                        .destinationCity(flight.getDestinationCity())
                        .destinationAirportShortName(flight.getDestinationAirportShortName())
                        .departureDate(flight.getDepartureDate())
                        .departureTime(flight.getDepartureTime())
                        .departureCity(flight.getDepartureCity())
                        .departureAirportShortName(flight.getDepartureAirportShortName())
                        .build());
        }};

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void deleteFlight_whenDateIsNull_thenNullPointerException() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flightDao.deleteFlight(EXISTING_FLIGHT, null);
    }

    @Test
    public void deleteFlight_whenFlightNumberIsNull_thenReturnZero() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        int expected = 0;
        int actual = flightDao.deleteFlight(null, flight.getDepartureDate());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteFlight_whenFlightNumberDoesNotExist_thenReturnZero() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        int expected = 0;
        int actual = flightDao.deleteFlight(NOT_EXISTING_FLIGHT, flight.getDepartureDate());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteFlight_whenFlightNumberExists_thenReturnOne() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        int expected = 1;
        int actual = flightDao.deleteFlight(EXISTING_FLIGHT, flight.getDepartureDate());

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void editFlight_whenDateIsNull_ThenNullPointerException() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flight.setDepartureDate(null);

        flightDao.editFlight(flight);
    }

    @Test(expected = NullPointerException.class)
    public void editFlight_whenTimeIsNull_ThenNullPointerException() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flight.setDepartureTime(null);

        flightDao.editFlight(flight);
    }

    @Test(expected = DaoException.class)
    public void editFlight_whenPlaneNumberIsNull_ThenDaoException() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flight.setPlaneNumber(null);

        flightDao.editFlight(flight);
    }

    @Test(expected = DaoException.class)
    public void editFlight_whenAirportIsNull_ThenDaoException() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flight.setDepartureAirportShortName(null);

        flightDao.editFlight(flight);
    }

    @Test(expected = NullPointerException.class)
    public void editFlight_whenFlightIsNull_ThenNullPointerException() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flightDao.editFlight(null);
    }

    @Test
    public void editFlight_whenFlightIsValid_ThenReturnOne() throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();
        flight.setCrew(CREW_NAME);
        flight.setPlaneNumber(PLANE_NUMBER);

        int expected = 1;
        int actual = flightDao.editFlight(flight);

        Assert.assertEquals(expected, actual);
    }
}