package by.epam.tr.dao.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.CloseOperation;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.ListCreatorDAO;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListCreatorImpl extends CloseOperation implements ListCreatorDAO {

    private static final String SELECT_CITY_WITH_AIRPORT = "SELECT cities.`name`,`name-abbreviation` FROM airport.airports\n" +
                                        "JOIN cities ON cities.id = airports.`city-id`;";

    private static final String SELECT_USERS_ROLES = "SELECT title from airport.roles";

    private static final String SELECT_CREWS = "SELECT `short-name` from airport.`flight-teams`";

    private static final String SELECT_USERS_BY_ROLE = "SELECT `name`, surname FROM airport.users WHERE `role-id`= (" +
                                        "SELECT id  FROM roles WHERE title = ?);";

    @Override
    public List<String> createCityWithAirportList() throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        List <String> citiesWithAirports = new ArrayList<>();
        try {
            connection = pool.takeConnection();
            st = connection.createStatement();

            rs = st.executeQuery(SELECT_CITY_WITH_AIRPORT);
            while (rs.next()) {
                citiesWithAirports.add(rs.getString("name")+"("+rs.getString("name-abbreviation")+")");
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!", e);
        } catch (SQLException e) {
            throw new DAOException("Exception during creating city with airports list!", e);
        }finally{
            closeAll(rs, st, pool, connection);
        }
        return citiesWithAirports;
    }

    @Override
    public List<String> createRolesList() throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        List <String> roles = new ArrayList<>();
        try {
            connection = pool.takeConnection();
            st = connection.createStatement();

            rs = st.executeQuery(SELECT_USERS_ROLES);
            while (rs.next()) {
                roles.add(rs.getString("title"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during creating city roles list!", e);
        }finally {
            closeAll(rs, st, pool, connection);
        }
        return roles;
    }

    @Override
    public List<String> createCrewsList() throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        List <String> crews = new ArrayList<>();
        try {
            connection = pool.takeConnection();
            st = connection.createStatement();

            rs = st.executeQuery(SELECT_CREWS);
            while (rs.next()) {
                crews.add(rs.getString("short-name"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during creating crews list!", e);
        }finally{
            closeAll(rs, st, pool, connection);
        }
        return crews;
    }

    @Override
    public List<User> createUserByRoleList(String role) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List <User> users = new ArrayList<>();
        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SELECT_USERS_BY_ROLE);

            ps.setString(1, role);

            rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getString("name"), rs.getString("surname")));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during creating users by role list!", e);
        }finally {
            closeAll(rs, ps, pool, connection);
        }
        return users;
    }
}