package by.epam.airport_system.dao.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.UserDao;
import by.epam.airport_system.dao.connectionpool.ConnectionPool;
import by.epam.airport_system.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao, CloseOperation {
    private final static String INSERT_USER =  "INSERT INTO airport.users (`role-id`, login, `password`, `name`, surname, email, `career-start-year`)" +
            "VALUES((SELECT id FROM airport.roles WHERE title = ?),?,?,?,?,?,?);";

    private final static String USER_BY_LOGIN = "SELECT login, title  AS `role`, `password`, `name`, surname, email, `career-start-year` " +
            "FROM users JOIN roles ON users.`role-id` = roles.id WHERE login = ?;";

    private final static String CHECK_USER_EXISTENCE_1 = "SELECT `name` FROM users WHERE login = ?;";

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

    private static final String USERS_ROLES = "SELECT title from airport.roles";
    private static final String USERS_BY_ROLE = "SELECT `name`, surname FROM airport.users WHERE `role-id`= (" +
            "SELECT id  FROM roles WHERE title = ?);";

    private final static String EDIT_USER = "UPDATE airport.users\n" +
            "SET `name` = ?, surname = ?,\n" +
            "email = ?, `career-start-year` = ?\n" +
            "WHERE login = ?";


    @Override
    public boolean signUpUser(User user) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        boolean flag = false;

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(INSERT_USER);

            ps.setString(1,user.getRole());
            ps.setString(2,user.getLogin());
            ps.setString(3,user.getPassword());
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
    public User getUserByLogin(String login) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user;

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(USER_BY_LOGIN);

            ps.setString(1, login);

            rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            }
            user = User.builder().login(rs.getString("login"))
                                .role(rs.getString("role"))
                                .password(rs.getString("password"))
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

    @Override
    public List<String> rolesList() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        List <String> roles = new ArrayList<>();

        try {
            connection = pool.takeConnection();
            st = connection.createStatement();

            rs = st.executeQuery(USERS_ROLES);
            while (rs.next()) {
                roles.add(rs.getString("title"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during creating roles list!", e);
        }finally {
            closeAll(rs, st, pool, connection);
        }
        return roles;
    }

    @Override
    public List<User> userByRoleList(String role) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List <User> users = new ArrayList<>();

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(USERS_BY_ROLE);

            ps.setString(1, role);

            rs = ps.executeQuery();
            while (rs.next()) {
                users.add(User.builder().name(rs.getString("name")).surname(rs.getString("surname")).build());
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during creating users by role list!", e);
        }finally {
            closeAll(rs, ps, pool, connection);
        }
        return users;
    }

    @Override
    public int editUser(User user) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(EDIT_USER);

            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getCareerStartYear());
            ps.setString(5, user.getLogin());

            return ps.executeUpdate();
        }catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during user editing!", e);
        }finally {
            closeAll(rs, ps, pool, connection);
        }
    }
}