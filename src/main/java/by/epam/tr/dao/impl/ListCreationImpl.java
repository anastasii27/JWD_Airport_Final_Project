package by.epam.tr.dao.impl;

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

    private ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public List<String> createCityWithAirportList() throws DAOException {

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

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during creating city roles list!");
        }finally{
            closeAll(rs, st, pool, connection);
        }

        return roles;
    }
}
