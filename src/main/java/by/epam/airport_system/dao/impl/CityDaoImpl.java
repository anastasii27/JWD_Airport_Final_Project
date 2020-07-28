package by.epam.airport_system.dao.impl;

import by.epam.airport_system.dao.CityDao;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.connectionpool.ConnectionPool;
import by.epam.airport_system.dao.connectionpool.ConnectionPoolException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityDaoImpl implements CityDao, CloseOperation {
    private static final String CITY_WITH_AIRPORT = "SELECT cities.`name`,`name-abbreviation` FROM airport.airports\n" +
            "JOIN airport.cities ON cities.id = airports.`city-id`;";

    private static final String CITY_WITH_AIRPORT_BY_COUNTRY = "SELECT cities.`name`,`name-abbreviation` FROM airport.airports\n" +
            "JOIN airport.cities ON cities.id = airports.`city-id`\n" +
            "JOIN airport.countries ON cities.`country-id` = countries.id\n" +
            "WHERE countries.`name` = ?;";

    @Override
    public List<String> cityWithAirportList() throws DaoException {
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
    public List<String> cityWithAirportList(String country) throws DaoException {
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
}
