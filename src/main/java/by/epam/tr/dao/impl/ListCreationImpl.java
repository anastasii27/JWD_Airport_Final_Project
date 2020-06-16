package by.epam.tr.dao.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.CloseOperation;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.ListCreationDAO;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListCreationImpl extends CloseOperation implements ListCreationDAO {

    private static final String SELECT_CITY_WITH_AIRPORT = "SELECT cities.`name`,`name-abbreviation` FROM airport.airports\n" +
                                                           "JOIN cities ON cities.id = airports.`city-id`;";

    private static final String SELECT_USERS_ROLES = "SELECT title from airport.roles";

    private static final String SELECT_CREWS = "SELECT `short-name` from airport.`flight-teams`";

    private static final String SELECT_USERS_BY_ROLE = "SELECT `name`, surname FROM airport.users WHERE `role-id`= (" +
                                                "SELECT id  FROM roles WHERE title = ?);";

    private ConnectionPool pool = ConnectionPool.getInstance();
    private Connection connection;
    private Statement st;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public List<String> createCityWithAirportList() throws DAOException {

        List <String> citiesWithAirports = new ArrayList<>();
        try {
            connection = pool.takeConnection();
            st = connection.createStatement();

            rs = st.executeQuery(SELECT_CITY_WITH_AIRPORT);

            while (rs.next()) {
                citiesWithAirports.add(rs.getString("name")+"("+rs.getString("name-abbreviation")+")");
            }

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during creating city with airports list!");
        }finally{
            closeAll(rs, st, pool, connection);
        }
        return citiesWithAirports;
    }

    @Override
    public List<String> createRolesList() throws DAOException {

        List <String> roles = new ArrayList<>();
        try {
            connection = pool.takeConnection();
            st = connection.createStatement();

            rs = st.executeQuery(SELECT_USERS_ROLES);

            while (rs.next()) {
                roles.add(rs.getString("title"));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during creating city roles list!");
        }finally {
            closeAll(rs, st, pool, connection);
        }
        return roles;
    }

    @Override
    public List<String> createCrewsList() throws DAOException {

        List <String> crews = new ArrayList<>();
        try {
            connection = pool.takeConnection();
            st = connection.createStatement();

            rs = st.executeQuery(SELECT_CREWS);

            while (rs.next()) {
                crews.add(rs.getString("short-name"));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during creating crews list!");
        }finally{
            closeAll(rs, st, pool, connection);
        }
        return crews;
    }

    @Override
    public List<User> createUserByRoleList(String role) throws DAOException {

        List <User> users = new ArrayList<>();
        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SELECT_USERS_BY_ROLE);

            ps.setString(1, role);

            rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getString("name"), rs.getString("surname")));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during creating users by role list!");
        }finally {
            closeAll(rs, st, pool, connection);
        }
        return users;
    }
}
