package by.epam.tr.dao.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.UserDao;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao, CloseOperation {
    private final static String INSERT_USER =  "INSERT INTO airport.users (`role-id`, login, `password`, `name`, surname, email, `career-start-year`)" +
            "VALUES((SELECT id FROM airport.roles WHERE title = ?),?,?,?,?,?,?);";

    private final static String SING_IN = "SELECT title  AS `role` , `name`, surname, email, `career-start-year` " +
            "FROM users JOIN roles ON users.`role-id` = roles.id WHERE login = ? AND `password`=?;";

    private final static String CHECK_USER_EXISTENCE_1 = "SELECT `name` FROM users WHERE login = ?;";

    private final static String SELECT_ALL_USERS = "SELECT `name`, surname, title FROM airport.users " +
            "JOIN roles ON roles.id = users.`role-id`;";

    private final static String BUSY_DEPARTURE_DISPATCHERS = "SELECT `name`, surname FROM airport.flights\n" +
            "JOIN users ON `dispatcher-id` = users.id\n" +
            "WHERE `departure-time` BETWEEN ? AND addtime(?, \"00:10:00\")\n" +
            "AND `departure-date` = ?\n" +
            "AND `departure-airport-id` = (SELECT id FROM airports WHERE `name-abbreviation` = ?)";

    private final static String BUSY_ARRIVAL_DISPATCHERS = "SELECT `name`, surname FROM airport.flights\n" +
            "JOIN users ON `dispatcher-id` = users.id\n" +
            "WHERE `destination-time` BETWEEN ? AND addtime(?, \"00:10:00\")\n" +
            "AND `destination-date` = ?\n" +
            "AND `destination-airport-id` = (SELECT id FROM airports WHERE `name-abbreviation` = ?)";

    @Override
    public boolean addNewUser(User user, String login, String password) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        boolean flag = false;

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(INSERT_USER);

            ps.setString(1,user.getRole());
            ps.setString(2,login);
            ps.setString(3,password);
            ps.setString(4,user.getName());
            ps.setString(5,user.getSurname());
            ps.setString(6,user.getEmail());
            ps.setString(7, user.getCareerStartYear());
            ps.executeUpdate();

            flag = true;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during user registration", e);
        }finally {
            closeAll(ps, pool, connection);
        }
        return flag;
    }

    @Override
    public User signIn(String login, String password) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user;

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SING_IN);

            ps.setString(1, login);
            ps.setString(2,password);

            rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            }
            user = User.builder().role(rs.getString("role"))
                                .name(rs.getString("name"))
                                .surname(rs.getString("surname"))
                                .email(rs.getString("email"))
                                .careerStartYear(rs.getString("career-start-year")).build();

        }catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during signing in!", e);
        }finally {
            closeAll(rs, ps, pool, connection);
        }
        return user;
    }

    @Override
    public boolean doesUserExist(String login) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(CHECK_USER_EXISTENCE_1);

            ps.setString(1, login);
            rs = ps.executeQuery();

            return rs.next();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during user existence operation!", e);
        }finally {
            closeAll(rs, ps, pool, connection);
        }
    }

    @Override
    public List<User> allUsers() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SELECT_ALL_USERS);

            rs = ps.executeQuery();
            while (rs.next()){
                users.add( User.builder().name(rs.getString("name"))
                                        .surname(rs.getString("surname"))
                                        .role( rs.getString("title")).build());
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during all users getting!", e);
        }finally {
            closeAll(rs, ps, pool, connection);
        }
        return users;
    }

    @Override
    public List<User> busyDepartureDispatchers(LocalDate date, LocalTime time, String airportName) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;

        try {
            connection = pool.takeConnection();
            return makeDispatcherList(date, time, airportName, BUSY_DEPARTURE_DISPATCHERS, connection);
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during busy departure dispatchers getting!", e);
        }finally {
            if(connection != null){
                pool.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<User> busyArrivalDispatchers(LocalDate date, LocalTime time, String airportName) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;

        try {
            connection = pool.takeConnection();
            return makeDispatcherList(date, time, airportName, BUSY_ARRIVAL_DISPATCHERS, connection);
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during busy arrival dispatchers getting!", e);
        }finally {
            if(connection != null){
                pool.releaseConnection(connection);
            }
        }
    }

    private List<User> makeDispatcherList(LocalDate date, LocalTime time, String airportName, String query, Connection connection) throws SQLException {
        List<User> dispatchers = new ArrayList<>();
        ResultSet rs;

        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setTime(1, Time.valueOf(time));
            ps.setTime(2, Time.valueOf(time));
            ps.setDate(3, Date.valueOf(date));
            ps.setString(4, airportName);

            rs = ps.executeQuery();
            while (rs.next()) {
                dispatchers.add(User.builder().name(rs.getString("name"))
                        .surname(rs.getString("surname")).build());
            }
            return dispatchers;
        }
    }
}