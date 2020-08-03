package by.epam.airport_system.dao.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.dao.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrewMemberDaoImplTest extends H2DataBaseCreation {
    private final static String ILLEGAL_CREW = "L9";
    private final static String EXISTING_CREW = "A1";
    private final static String CREW_WITHOUT_MAIN_PILOT = "N1";
    private User steward;
    private User dispatcher;
    private User commander;

    @Before
    public void setUp(){
        commander = User.builder().name( "Владислав")
                .surname("Ясницкий").build();
        steward = User.builder().name("Мария")
                .surname("Аленская")
                .role("steward")
                .email("alienskaya17@gmail.com").build();
        dispatcher = User.builder().name("Аня")
                .surname("Корытько").build();
    }

    @Test
    public void crewMembers_whenCrewNameExists_thenList() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        commander.setRole("pilot");
        commander.setEmail("vlad-yas@gmail.com");

        List<User> expected = new ArrayList<User>(){{
                add(steward);
                add(commander);
        }};
        List<User> actual = crewMemberDao.crewMembers(EXISTING_CREW);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void crewMembers_whenCrewNameDoesNotExist_thenEmptyList() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        List<User> actual = crewMemberDao.crewMembers(ILLEGAL_CREW);

        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void crewMembers_whenCrewNameIsNull_thenEmptyList() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        List<User> actual = crewMemberDao.crewMembers(null);

        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void deleteCrewMember_whenCrewNameIsNull_thenReturnZero() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        int expected = 0;
        int actual = crewMemberDao.deleteCrewMember(null, steward);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void deleteCrewMember_whenUserIsNull_thenNullPointerException() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        crewMemberDao.deleteCrewMember(EXISTING_CREW, null);
    }

    @Test
    public void deleteCrewMember_whenCrewNameDoesNotExist_thenReturnZero() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        int expected = 0;
        int actual = crewMemberDao.deleteCrewMember(ILLEGAL_CREW, steward);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteCrewMember_whenUserIsInTheCrew_thenReturnOne() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        int expected = 1;
        int actual = crewMemberDao.deleteCrewMember(EXISTING_CREW, steward);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteCrewMember_whenUserIsNotInTheCrew_thenReturnZero() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        int expected = 0;
        int actual = crewMemberDao.deleteCrewMember(EXISTING_CREW, dispatcher);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addCrewMember_whenUserIsNotInThisCrew_thenReturnOne() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        List<User> users = new ArrayList<User>(){{
            add(dispatcher);
        }};
        int expected = 1;
        int actual = crewMemberDao.addCrewMember(EXISTING_CREW, users);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = DaoException.class)
    public void addCrewMember_whenUserIsAlreadyInThisCrew_thenDaoException() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        List<User> users = new ArrayList<User>(){{
            add(steward);
        }};

        crewMemberDao.addCrewMember(EXISTING_CREW, users);
    }

    @Test(expected = NullPointerException.class)
    public void addCrewMember_whenUserIsNull_thenNullPointerException() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        List<User> users = new ArrayList<User>(){{
            add(null);
        }};

        crewMemberDao.addCrewMember(EXISTING_CREW, users);
    }

    @Test(expected = NullPointerException.class)
    public void addCrewMember_whenListIsNull_thenNullPointerException() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        crewMemberDao.addCrewMember(EXISTING_CREW, null);
    }

    @Test(expected = DaoException.class)
    public void addCrewMember_whenCrewDoesNotExist_thenDaoException() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        List<User> users = new ArrayList<User>(){{
            add(dispatcher);
        }};

        crewMemberDao.addCrewMember(ILLEGAL_CREW, users);
    }

    @Test(expected = DaoException.class)
    public void addCrewMember_whenCrewIsNull_thenDaoException() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        List<User> users = new ArrayList<User>(){{
            add(dispatcher);
        }};

        crewMemberDao.addCrewMember(null, users);
    }

    @Test
    public void isUserInTheCrew_whenUserIsInTheCrew_thenTrue() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        boolean actual = crewMemberDao.isUserInTheCrew(EXISTING_CREW, steward);

        Assert.assertTrue(actual);
    }

    @Test
    public void isUserInTheCrew_whenUserIsNotInTheCrew_thenFalse() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        boolean actual = crewMemberDao.isUserInTheCrew(EXISTING_CREW, dispatcher);

        Assert.assertFalse(actual);
    }

    @Test(expected = NullPointerException.class)
    public void isUserInTheCrew_whenUserIsNull_thenNullPointerException() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        crewMemberDao.isUserInTheCrew(EXISTING_CREW, null);
    }

    @Test
    public void isUserInTheCrew_whenCrewIsNull_thenFalse() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        boolean actual = crewMemberDao.isUserInTheCrew(null, dispatcher);

        Assert.assertFalse(actual);
    }

    @Test
    public void isUserInTheCrew_whenCrewDoesNotExist_thenFalse() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        boolean actual = crewMemberDao.isUserInTheCrew(ILLEGAL_CREW, dispatcher);

        Assert.assertFalse(actual);
    }

    @Test
    public void findMainPilot_whenCrewExists_thenUser() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        User actual = crewMemberDao.findMainPilot(EXISTING_CREW);

        Assert.assertEquals(commander, actual);
    }

    @Test
    public void findMainPilot_whenCrewDoesNotExist_thenNull() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        User actual = crewMemberDao.findMainPilot(ILLEGAL_CREW);

        Assert.assertNull(actual);
    }

    @Test
    public void findMainPilot_whenCrewDoesNotHasMainPilot_thenNull() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        User actual = crewMemberDao.findMainPilot(CREW_WITHOUT_MAIN_PILOT);

        Assert.assertNull(actual);
    }

    @Test
    public void findMainPilot_whenCrewIsNull_thenNull() throws DaoException {
        CrewMemberDao crewMemberDao = DaoFactory.getInstance().getCrewMemberDAO();
        User actual = crewMemberDao.findMainPilot(null);

        Assert.assertNull(actual);
    }
}