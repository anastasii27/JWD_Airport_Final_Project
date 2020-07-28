package by.epam.airport_system.dao.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.User;
import by.epam.airport_system.dao.CrewDao;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.dao.H2DataBaseCreation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.*;

public class CrewDaoImplTest extends H2DataBaseCreation {
    private final static String ILLEGAL_CREW = "L9";
    private final static String EXISTING_CREW = "A1";
    private final static String CREW_WITHOUT_MAIN_PILOT = "N1";
    private final static String NEW_CREW = "A3";
    private final static String ILLEGAL_FLIGHT_NUMBER = "K 0000";
    private final static String EXISTING_FLIGHT_NUMBER = "KL 2112";
    private final static String FLIGHT_NUMBER_WITHOUT_CREW = "TR 1718";
    private final static String PARAM1 = "first_pilot";
    private final static String PARAM2 = "steward";
    private User steward;
    private User commander;
    private Flight flight;

    @Before
    public void setUp(){
        commander = User.builder().name( "Владислав")
                .surname("Ясницкий").build();
        steward = User.builder().name("Мария")
                .surname("Аленская").build();
        flight = Flight.builder().departureDate(LocalDate.of(2020, 7, 26)).build();
    }

    @Test
    public void doesCrewNameExist_whenCrewExists_thenTrue() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        boolean actual = crewDao.doesCrewNameExist(EXISTING_CREW);

        Assert.assertTrue(actual);
    }

    @Test
    public void doesCrewNameExist_whenCrewDoesNotExist_thenFalse() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        boolean actual = crewDao.doesCrewNameExist(ILLEGAL_CREW);

        Assert.assertFalse(actual);
    }

    @Test
    public void deleteCrew_whenCrewExists_thenReturnOne() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        int expected = 1;
        int actual = crewDao.deleteCrew(EXISTING_CREW);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteCrew_whenCrewDoesNotExist_thenReturnZero() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        int expected = 0;
        int actual = crewDao.deleteCrew(ILLEGAL_CREW);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteCrew_whenCrewIsNull_thenReturnZero() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        int expected = 0;
        int actual = crewDao.deleteCrew(null);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findMainPilot_whenCrewExists_thenUser() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        User actual = crewDao.findMainPilot(EXISTING_CREW);

        Assert.assertEquals(commander, actual);
    }

    @Test
    public void findMainPilot_whenCrewDoesNotExist_thenNull() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        User actual = crewDao.findMainPilot(ILLEGAL_CREW);

        Assert.assertNull(actual);
    }

    @Test
    public void findMainPilot_whenCrewDoesNotHasMainPilot_thenNull() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        User actual = crewDao.findMainPilot(CREW_WITHOUT_MAIN_PILOT);

        Assert.assertNull(actual);
    }

    @Test
    public void findMainPilot_whenCrewIsNull_thenNull() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        User actual = crewDao.findMainPilot(null);

        Assert.assertNull(actual);
    }
    @Test
    public void allCrews() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        List<String> expected = new ArrayList<String>(){{
            add("A1");
            add("A2");
            add("N1");
        }};
        List<String> actual = crewDao.allCrews();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setCrewForFlight_whenCrewIsNull_thenReturnOne() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        int expected = 1;
        int actual = crewDao.setCrewForFlight(null, EXISTING_FLIGHT_NUMBER);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setCrewForFlight_whenFlightIsNull_thenReturnZero() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        int expected = 0;
        int actual = crewDao.setCrewForFlight(EXISTING_CREW, null);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setCrewForFlight_whenCrewDoesNotExist_thenReturnOne() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        int expected = 1;
        int actual = crewDao.setCrewForFlight(ILLEGAL_CREW, EXISTING_FLIGHT_NUMBER);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setCrewForFlight_whenCrewExists_thenReturnOne() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        int expected = 1;
        int actual = crewDao.setCrewForFlight(EXISTING_CREW, EXISTING_FLIGHT_NUMBER);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void flightCrew_whenFlightExists_thenCrewName() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        flight.setFlightNumber(EXISTING_FLIGHT_NUMBER);

        String actual = crewDao.flightCrew(flight);

        Assert.assertEquals(EXISTING_CREW, actual);
    }

    @Test
    public void flightCrew_whenFlightDoesNotExist_thenNull() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        flight.setFlightNumber(ILLEGAL_FLIGHT_NUMBER);

        String actual = crewDao.flightCrew(flight);

        Assert.assertNull(actual);
    }

    @Test
    public void flightCrew_whenFlightIsWithoutCrew_thenNull() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        flight.setFlightNumber(FLIGHT_NUMBER_WITHOUT_CREW);

        String actual = crewDao.flightCrew(flight);

        Assert.assertNull(actual);
    }

    @Test
    public void takenOnFlightsCrews() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        List<String> expected = new ArrayList<String>(){{
            add(EXISTING_CREW);
        }};
        List<String> actual = crewDao.takenOnFlightsCrews();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createCrew_whenCrewAndUserExist_thenTrue() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        Map<String, User> users = new HashMap<String, User>(){{
            put(PARAM1, commander);
            put(PARAM2, steward);
        }};
        boolean actual = crewDao.createCrew(NEW_CREW, users);

        Assert.assertTrue(actual);
    }

    @Test(expected = DaoException.class)
    public void createCrew_whenCrewIsNull_thenDaoException() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        Map<String, User> users = new HashMap<String, User>(){{
            put(PARAM1, commander);
            put(PARAM2, steward);
        }};

        crewDao.createCrew(null, users);
    }

    @Test(expected = NullPointerException.class)
    public void createCrew_whenMembersAreNull_thenNullPointerException() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        crewDao.createCrew(NEW_CREW, null);
    }

    @Test(expected = NullPointerException.class)
    public void createCrew_whenMainPilotIsNull_thenNullPointerException() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        Map<String, User> users = new HashMap<String, User>(){{
            put(PARAM1, null);
            put(PARAM2, steward);
        }};

        crewDao.createCrew(NEW_CREW, users);
    }

    @Test
    public void createCrew_whenThereAreNoStewards_thenTrue() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        Map<String, User> users = new HashMap<String, User>(){{
            put(PARAM1, commander);
        }};
        boolean actual = crewDao.createCrew(NEW_CREW, users);

        Assert.assertTrue(actual);
    }

    @Test(expected = NullPointerException.class)
    public void createCrew_whenThereAreNoMembers_thenNullPointerException() throws DaoException {
        CrewDao crewDao = DaoFactory.getInstance().getCrewDAO();
        Map<String, User> users = Collections.emptyMap();

        crewDao.createCrew(NEW_CREW, users);
    }
}