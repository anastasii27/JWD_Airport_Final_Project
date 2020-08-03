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

public class UserFlightsDaoImplTest extends H2DataBaseCreation {
    private Flight flight;
    private User user;

    @Before
    public void setUp() throws Exception {
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

        user = User.builder().name("Nastya")
                .surname("Rodnova")
                .email("nastya1@gmail.com")
                .role("pilot")
                .careerStartYear("2019")
                .login("nastya1234")
                .password("1234567")
                .build();
    }

    @Test
    public void userFlights_whenIdDoesNotExist_thenEmptyList() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        List<Flight> actual = userFlightsDao.userFlights(0, LocalDate.now());

        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void userFlights_whenIdExistsAndUserHasFlights_thenList() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        List<Flight> expected = new ArrayList<Flight>(){{
            add(flight);
        }};
        List<Flight> actual = userFlightsDao.userFlights(1, flight.getDepartureDate());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void userFlights_whenIdExistsAndUserHasNoFlights_thenEmptyList() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        List<Flight> actual = userFlightsDao.userFlights(4, LocalDate.now());

        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test(expected = NullPointerException.class)
    public void userFlights_whenDateIsNull_thenNullPointerException() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        userFlightsDao.userFlights(1, null);
    }

    @Test
    public void dispatcherFlights() throws DaoException {

    }

    @Test(expected = NullPointerException.class)
    public void lastUserFlightBeforeDate_whenUserIsNull_thenNullPointerException() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        userFlightsDao.lastUserFlightBeforeDate(null, LocalDate.now());
    }

    @Test(expected = NullPointerException.class)
    public void lastUserFlightBeforeDate_whenDateIsNull_thenNullPointerException() throws DaoException {
        UserFlightsDao userFlightsDao = DaoFactory.getInstance().getUserFlightsDao();
        userFlightsDao.lastUserFlightBeforeDate(user, null);
    }

    @Test
    public void firstUserFlightAfterDate() {
    }
}