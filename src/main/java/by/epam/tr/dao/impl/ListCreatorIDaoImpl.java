package by.epam.tr.dao.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.CloseOperation;
import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.ListCreatorDao;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListCreatorIDaoImpl extends CloseOperation implements ListCreatorDao {
    private static final String CITY_WITH_AIRPORT = "SELECT cities.`name`,`name-abbreviation` FROM airport.airports\n" +
            "JOIN cities ON cities.id = airports.`city-id`;";

    private static final String CITY_WITH_AIRPORT_BY_COUNTRY = "SELECT cities.`name`,`name-abbreviation` FROM airport.airports\n" +
            "JOIN cities ON cities.id = airports.`city-id`\n" +
            "JOIN countries ON cities.`country-id` = countries.id\n" +
            "WHERE countries.`name` = ?;";

    private static final String USERS_ROLES = "SELECT title from airport.roles";
    private static final String CREWS = "SELECT `short-name` from airport.`flight-teams`";
    private static final String COUNTRIES = "SELECT `name` FROM airport.countries;";
    private static final String USERS_BY_ROLE = "SELECT `name`, surname FROM airport.users WHERE `role-id`= (" +
            "SELECT id  FROM roles WHERE title = ?);";

    @Override
    public List<String> createCityWithAirportList() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(CITY_WITH_AIRPORT);

            return citiesWithAirports(ps);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Exception during taking connection!", e);
        } catch (SQLException e) {
            throw new DaoException("Exception during creating city with airports list!", e);
        }finally{
            closeAll(ps, pool, connection);
        }
    }

    @Override
    public List<String> createCityWithAirportList(String country) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = pool.takeConnection();

            ps =  connection.prepareStatement(CITY_WITH_AIRPORT_BY_COUNTRY);
            ps.setString(1, country);

            return citiesWithAirports(ps);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Exception during taking connection!", e);
        } catch (SQLException e) {
            throw new DaoException("Exception during creating city with airports list!", e);
        }finally{
            closeAll(ps, pool, connection);
        }
    }

    private List<String> citiesWithAirports(PreparedStatement ps) throws SQLException {
        List <String> citiesWithAirports = new ArrayList<>();
        ResultSet rs;

        rs = ps.executeQuery();
        while (rs.next()) {
            citiesWithAirports.add(rs.getString("name")+"("+rs.getString("name-abbreviation")+")");
        }

        return citiesWithAirports;
    }
    @Override
    public List<String> createRolesList() throws DaoException {
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
    public List<String> createCrewsList() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        List <String> crews = new ArrayList<>();

        try {
            connection = pool.takeConnection();
            st = connection.createStatement();

            rs = st.executeQuery(CREWS);
            while (rs.next()) {
                crews.add(rs.getString("short-name"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during creating crews list!", e);
        }finally{
            closeAll(rs, st, pool, connection);
        }
        return crews;
    }

    @Override
    public List<User> createUserByRoleList(String role) throws DaoException {
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
    public List<String> createCountriesList() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        List<String> countries = new ArrayList<>();

        try {
            connection = pool.takeConnection();
            st = connection.createStatement();

            rs = st.executeQuery(COUNTRIES);
            while (rs.next()) {
                countries.add(rs.getString("name"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during creating countries list!", e);
        } finally {
            closeAll(rs, st, pool, connection);
        }
        return countries;
    }
}