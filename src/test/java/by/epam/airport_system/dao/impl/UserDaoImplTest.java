package by.epam.airport_system.dao.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.dao.H2DataBaseCreation;
import by.epam.airport_system.dao.UserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoImplTest extends H2DataBaseCreation {
    private static final String WRONG_LOGIN = "hiThere";
    private static final String PASSWORD = "8181881";
    private static final String ILLEGAL_ROLE = "teacher";
    private User user;
    private User dispatcher;

    @Before
    public void setUp() throws Exception {
        user = User.builder().name("Nastya")
                .surname("Rodnova")
                .email("nastya1@gmail.com")
                .role("pilot")
                .careerStartYear("2019")
                .login("nastya1234")
                .password("1234567")
                .build();

        dispatcher =User.builder().id(2)
                .name("Аня")
                .surname("Корытько")
                .email("anni111@gmail.com")
                .role("dispatcher")
                .careerStartYear("2003")
                .login("anya333")
                .password("12345")
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void signUpUser_whenUserIsNull_thenNullPointerException() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        userDao.signUpUser(null);
    }

    @Test
    public void signUpUser_whenUserIsNotNull_thenTrue() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        boolean actual = userDao.signUpUser(user);

        Assert.assertTrue(actual);
    }

    @Test
    public void getUserByLogin_whenLoginIsNull_ThenNull() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        User actual = userDao.getUserByLogin(null);

        Assert.assertNull(actual);
    }

    @Test
    public void getUserByLogin_whenLoginIsWrong_ThenNull() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        User actual = userDao.getUserByLogin(WRONG_LOGIN);

        Assert.assertNull(actual);
    }

    @Test
    public void getUserByLogin_whenLoginExists_ThenUser() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        User actual = userDao.getUserByLogin(dispatcher.getLogin());

        Assert.assertEquals(dispatcher, actual);
    }

    @Test
    public void usersListByRole_whenRoleIsNull_thenEmptyList() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        List<User> actual = userDao.usersListByRole(null);

        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void usersListByRole_whenRoleDoesNotExist_thenEmptyList() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        List<User> actual = userDao.usersListByRole(ILLEGAL_ROLE);

        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void usersListByRole_whenRoleExist_thenList() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        List<User> expected = new ArrayList<User>(){{
            add(User.builder().name(dispatcher.getName()).surname(dispatcher.getSurname()).build());
        }};
        List<User> actual = userDao.usersListByRole(dispatcher.getRole());

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void editUser_whenUserIsNull_thenNullPointerException() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        userDao.editUser(null);
    }

    @Test
    public void editUser_whenUserDoesNotExist_thenReturnZero() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        int expected = 0;
        int actual = userDao.editUser(user);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void editUser_whenUserExists_thenReturnOne() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        int expected = 1;
        dispatcher.setName(user.getName());
        int actual = userDao.editUser(dispatcher);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void changeLogin_whenUserIsNull_thenNullPointerException() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        userDao.changeLogin(WRONG_LOGIN, null);
    }

    @Test(expected = DaoException.class)
    public void changeLogin_whenLoginIsNull_thenDaoException() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        userDao.changeLogin(null, dispatcher);
    }

    @Test
    public void changeLogin_whenUserDoesNotExist_thenReturnZero() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        int expected = 0;
        int actual = userDao.changeLogin(WRONG_LOGIN, user);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeLogin_whenUserExists_thenReturnOne() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        int expected = 1;
        int actual = userDao.changeLogin(WRONG_LOGIN, dispatcher);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void changePassword_whenUserIsNull_thenNullPointerException() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        userDao.changePassword(PASSWORD, null);
    }

    @Test
    public void changePassword_whenPasswordIsNull_thenReturnOne() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        int expected =1;
        int actual = userDao.changePassword(null, dispatcher);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changePassword_whenUserDoesNotExist_thenReturnZero() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        int expected = 0;
        int actual = userDao.changePassword(PASSWORD, user);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changePassword_whenUserExists_thenReturnOne() throws DaoException {
        UserDao userDao = DaoFactory.getInstance().getUserDAO();
        int expected = 1;
        int actual = userDao.changePassword(PASSWORD, dispatcher);

        Assert.assertEquals(expected, actual);
    }
}