package by.epam.airport_system.dao.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.User;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.dao.H2DataBaseCreation;
import by.epam.airport_system.dao.UserFlightsDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserFlightsDaoImplTest extends H2DataBaseCreation {//todo missed methods
    private Flight flight;
    private User steward;
    private User pilot;
    private User steward1;

    @Before
    public void setUp(){
        flight = Flight.builder().planeModel("Airbus 123")
                .flightNumber("KL 2112")
                .status("Scheduled")
                .destinationDate(LocalDate.of(2020,  7,26))
                .destinationTime(LocalTime.of(20, 53))
                .destinationCity("Minsk")
                .destinationAirportShortName("MSQ2")
                .departureDate(LocalDate.of(2020,  7,26))
                .departureTime(LocalTime.of(19, 53))
                .departureCity("Minsk")
                .departureAirportShortName("MSQ1")
                .build();

        steward = User.builder().name("Мария")
                .surname("Аленская")
                .role("steward")
                .email("alienskaya17@gmail.com").build();

        pilot =  User.builder().name("Владивлав")
                .surname("Ясницкий")
                .email("vlad@gmail.com").build();

        steward1 = User.builder().name("Анастасия")
                .surname("Роднова")
                .email("rodnovanastya@gmail.com").build();
    }

    @Test
    public void userFlights_whenUserWithThisIdDoesNotExist_thenEmptyList() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        List<Flight> actual = userFlightsDao.userFlights(0, LocalDate.now());

        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void userFlights_whenUserWithThisIdExistsAndHasFlights_thenList() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        List<Flight> expected = new ArrayList<Flight>(){{
            add(flight);
        }};
        List<Flight> actual = userFlightsDao.userFlights(1, flight.getDepartureDate());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userFlights_whenUserWithThisIdExistsAndHasNoFlights_thenEmptyList() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        List<Flight> actual = userFlightsDao.userFlights(4, LocalDate.now());

        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test(expected = NullPointerException.class)
    public void userFlights_whenDateIsNull_thenNullPointerException() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        userFlightsDao.userFlights(1, null);
    }

    @Test(expected = NullPointerException.class)
    public void lastUserFlightBeforeDate_whenUserIsNull_thenNullPointerException() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        userFlightsDao.lastUserFlightBeforeDate(null, LocalDate.now());
    }

    @Test(expected = NullPointerException.class)
    public void lastUserFlightBeforeDate_whenDateIsNull_thenNullPointerException() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        userFlightsDao.lastUserFlightBeforeDate(steward, null);
    }

    @Test
    public void lastUserFlightBeforeDate_whenUserExistsAndHasFlight_thenFlight() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        Flight expected = Flight.builder().destinationDate(flight.getDestinationDate())
                        .destinationTime(flight.getDestinationTime())
                        .destinationAirportShortName(flight.getDestinationAirportShortName()).build();

        Flight actual = userFlightsDao.lastUserFlightBeforeDate(steward, flight.getDepartureDate());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lastUserFlightBeforeDate_whenUserDoesNotExist_thenNull() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        Flight actual = userFlightsDao.lastUserFlightBeforeDate(pilot, flight.getDepartureDate());

        Assert.assertNull(actual);
    }

    @Test
    public void lastUserFlightBeforeDate_whenUserExistsAndHasNoFlight_thenNull() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        Flight actual = userFlightsDao.lastUserFlightBeforeDate(steward1, flight.getDepartureDate());

        Assert.assertNull(actual);
    }

    @Test(expected = NullPointerException.class)
    public void firstUserFlightAfterDate_whenUserIsNull_thenNullPointerException() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        userFlightsDao.firstUserFlightAfterDate(null, LocalDate.now());
    }

    @Test(expected = NullPointerException.class)
    public void firstUserFlightAfterDate_whenDateIsNull_thenNullPointerException() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        userFlightsDao.firstUserFlightAfterDate(steward, null);
    }

    @Test
    public void firstUserFlightAfterDate_whenUserExistsAndHasFlight_thenFlight() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        Flight expected = Flight.builder().departureDate(flight.getDepartureDate())
                .departureTime(flight.getDepartureTime())
                .departureAirportShortName(flight.getDepartureAirportShortName()).build();

        Flight actual = userFlightsDao.firstUserFlightAfterDate(steward, flight.getDepartureDate().minusDays(1));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void firstUserFlightAfterDate_whenUserDoesNotExist_thenNull() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        Flight actual = userFlightsDao.firstUserFlightAfterDate(pilot, flight.getDepartureDate());

        Assert.assertNull(actual);
    }

    @Test
    public void firstUserFlightAfterDate_whenUserExistsAndHasNoFlight_thenNull() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        Flight actual = userFlightsDao.firstUserFlightAfterDate(steward1, flight.getDepartureDate());

        Assert.assertNull(actual);
    }
}