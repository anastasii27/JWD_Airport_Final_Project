package by.epam.tr.dao.impl;

import by.epam.tr.bean.Plane;
import by.epam.tr.dao.CloseOperation;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.PlaneDao;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaneDaoImpl extends CloseOperation implements PlaneDao{
    private final static String PLANES_FROM_AIRPORT = "SELECT model, count(`plane-id`) as amount\n"+
                                        "FROM airport.flights\n"+
                                        "JOIN planes on `plane-id` = planes.id\n"+
                                        "WHERE `status` = 'Scheduled'\n"+
                                        "AND `destination-airport-id` = (SELECT id FROM airports WHERE `name-abbreviation` = ?)\n"+
                                        "AND `destination-date` = ?\n"+
                                        "GROUP BY `plane-id`;";

    private final static String ALREADY_TAKEN_ON_FLIGHT_PLANES = "SELECT model, count(`plane-id`) as amount\n" +
                                        "FROM airport.flights\n" +
                                        "JOIN planes on `plane-id` = planes.id\n" +
                                        "WHERE `status` = 'Scheduled'\n" +
                                        "AND `departure-airport-id` = (SELECT id FROM airports WHERE `name-abbreviation` = ?)\n" +
                                        "AND `departure-date` = ?\n" +
                                        "GROUP BY `plane-id`;";
    @Override
    public List<Plane> allPlanesFromAirport(String airportName, LocalDate destinationDate) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;

        try {
            pool.poolInitialization();
            connection = pool.takeConnection();

            return makePlanesList(PLANES_FROM_AIRPORT, airportName, destinationDate, connection);
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during getting airport planes list!", e);
        }finally {
            if (pool != null) {
                pool.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Plane> takenOnFlightPlanes(String airportName, LocalDate departureDate) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;

        try {
            pool.poolInitialization();
            connection = pool.takeConnection();

            return makePlanesList(ALREADY_TAKEN_ON_FLIGHT_PLANES, airportName, departureDate, connection);
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during getting planes on flight list!", e);
        }finally {
            if (pool != null) {
                pool.releaseConnection(connection);
            }
        }
    }

    private List<Plane> makePlanesList(String query, String airportName, LocalDate date, Connection connection) throws SQLException {
        List<Plane> planes = new ArrayList<>();
        ResultSet rs;

        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, airportName);
            ps.setDate(2, Date.valueOf(date));

            rs = ps.executeQuery();
            while (rs.next()) {
                planes.add(new Plane(rs.getString("model"), rs.getInt("amount")));
            }
        }
        return planes;
    }
}
