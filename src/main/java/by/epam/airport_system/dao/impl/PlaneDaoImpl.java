package by.epam.airport_system.dao.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.Plane;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.PlaneDao;
import by.epam.airport_system.dao.connectionpool.ConnectionPool;
import by.epam.airport_system.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PlaneDaoImpl implements PlaneDao, CloseOperation{
    private final static String PLANES_AT_AIRPORT = "SELECT `number`, title \n"+
            "FROM airport.flights\n"+
            "JOIN planes ON `plane-id` = planes.id\n" +
            "JOIN `plane-models` ON `plane-models`.id = (SELECT `model-id` FROM planes WHERE planes.id = `plane-id`)\n"+
            "WHERE `status` = 'Scheduled'\n"+
            "AND `destination-airport-id` = (SELECT id FROM airports WHERE `name-abbreviation` = ?)\n"+
            "AND `destination-date` < ?\n" +
            "GROUP BY `plane-id`;";

    private final static String TAKEN_ON_FLIGHT_PLANES_AT_AIRPORT = "SELECT `number`, title \n" +
            "FROM airport.flights\n" +
            "JOIN planes ON `plane-id` = planes.id\n" +
            "JOIN `plane-models` ON `plane-models`.id = (SELECT `model-id` FROM planes WHERE planes.id = `plane-id`)\n" +
            "WHERE `status` = 'Scheduled'\n" +
            "AND `departure-airport-id` = (SELECT id FROM airports WHERE `name-abbreviation` = ?)\n" +
            "AND `departure-date` < ?\n" +
            "GROUP BY `plane-id`;";

    private final static String ALL_PLANES_NUMBERS = "SELECT `number`, title FROM airport.planes\n" +
            "JOIN `plane-models` ON `plane-models`.id = `model-id`";

    private final static String FIRST_PLANE_FLIGHT_AFTER_DATE = "SELECT `departure-date`, `departure-time`,\n" +
            "`name-abbreviation` AS `dep-airport-short-name`\n" +
            "FROM airport.flights\n" +
            "JOIN airports ON `departure-airport-id` = airports.id\n" +
            "WHERE `plane-id` = (SELECT id FROM planes WHERE `number` = ?)\n" +
            "AND `departure-date` > ? \n" +
            "ORDER BY `departure-date`, `departure-time` LIMIT 1;";

    @Override
    public List<Plane> arrivedToAirportPlane(String airportName, LocalDate date) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;

        try {
            connection = pool.takeConnection();

            return makePlanesList(PLANES_AT_AIRPORT, airportName, date, connection);
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during getting airport planes list!", e);
        }finally {
            if (pool != null) {
                pool.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Plane> takenOnFlightPlanes(String airportName, LocalDate date) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;

        try {
            connection = pool.takeConnection();

            return makePlanesList(TAKEN_ON_FLIGHT_PLANES_AT_AIRPORT,  airportName, date, connection);
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during getting planes on flight list!", e);
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
                planes.add(new Plane(rs.getString("title"),rs.getString("number")));
            }
        }
        return planes;
    }

    public List<Plane> allPlanes() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement st = null;
        ResultSet rs;
        List<Plane> planes = new ArrayList<>();

        try {
            connection = pool.takeConnection();
            st = connection.createStatement();

            rs = st.executeQuery(ALL_PLANES_NUMBERS);
            while (rs.next()) {
                planes.add(new Plane(rs.getString("title"),rs.getString("number")));
            }
            return planes;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during getting planes on flight list!", e);
        }finally {
            closeAll(st, pool, connection);
        }
    }

    @Override
    public Flight firstPlaneFlightAfterDate(String planeNumber, LocalDate date) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(FIRST_PLANE_FLIGHT_AFTER_DATE);

            ps.setString(1, planeNumber);
            ps.setDate(2, Date.valueOf(date));

            rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            }

            Flight flight = Flight.builder().departureDate(LocalDate.parse(rs.getString("departure-date")))
                                            .departureTime(LocalTime.parse(rs.getString("departure-time")))
                                            .departureAirportShortName(rs.getString("dep-airport-short-name"))
                                            .build();

            return flight;
        }catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during searching first plane flight after date", e);
        }finally {
            closeAll(rs, ps, pool, connection);
        }
    }
}
