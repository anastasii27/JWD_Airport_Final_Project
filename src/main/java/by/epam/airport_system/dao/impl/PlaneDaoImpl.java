package by.epam.airport_system.dao.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.Plane;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.PlaneDao;
import by.epam.airport_system.dao.connectionpool.ConnectionPool;
import by.epam.airport_system.dao.connectionpool.ConnectionPoolException;
import static by.epam.airport_system.dao.impl.DbParameterName.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PlaneDaoImpl implements PlaneDao{
    private final static String PLANES_AT_AIRPORT = "SELECT `number`, title \n"+
            "FROM airport.flights\n"+
            "JOIN airport.planes ON `plane-id` = planes.id\n" +
            "JOIN airport.`plane-models` ON `plane-models`.id = (SELECT `model-id` FROM airport.planes WHERE planes.id = `plane-id`)\n"+
            "WHERE `status` = 'Scheduled'\n"+
            "AND `destination-airport-id` = (SELECT id FROM airport.airports WHERE `name-abbreviation` = ?)\n"+
            "AND `destination-date` < ?\n" +
            "GROUP BY `plane-id`;";

    private final static String TAKEN_ON_FLIGHT_PLANES_AT_AIRPORT = "SELECT `number`, title \n" +
            "FROM airport.flights\n" +
            "JOIN airport.planes ON `plane-id` = planes.id\n" +
            "JOIN airport.`plane-models` ON `plane-models`.id = (SELECT `model-id` FROM airport.planes WHERE planes.id = `plane-id`)\n" +
            "WHERE `status` = 'Scheduled'\n" +
            "AND `departure-airport-id` = (SELECT id FROM airport.airports WHERE `name-abbreviation` = ?)\n" +
            "AND `departure-date` < ?\n" +
            "GROUP BY `plane-id`;";

    private final static String ALL_PLANES_NUMBERS = "SELECT `number`, title FROM airport.planes\n" +
            "JOIN airport.`plane-models` ON `plane-models`.id = `model-id`";

    private final static String FIRST_PLANE_FLIGHT_AFTER_DATE = "SELECT `departure-date`, `departure-time`,\n" +
            "`name-abbreviation` AS `dep-airport-short-name`\n" +
            "FROM airport.flights\n" +
            "JOIN  airport.airports ON `departure-airport-id` = airports.id\n" +
            "WHERE `plane-id` = (SELECT id FROM  airport.planes WHERE `number` = ?)\n" +
            "AND `departure-date` > ? \n" +
            "ORDER BY `departure-date`, `departure-time` LIMIT 1;";

    @Override
    public List<Plane> arrivedToAirportPlanes(String airportName, LocalDate date) throws DaoException {
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
                planes.add(new Plane(rs.getString(TITLE),rs.getString(NUMBER)));
            }
        }
        return planes;
    }

    public List<Plane> allPlanes() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        List<Plane> planes = new ArrayList<>();

        try {
            connection = pool.takeConnection();
            st = connection.createStatement();

            rs = st.executeQuery(ALL_PLANES_NUMBERS);
            while (rs.next()) {
                planes.add(new Plane(rs.getString(TITLE),rs.getString(NUMBER)));
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during getting planes on flight list!", e);
        }finally {
            if(pool != null) {
                pool.closeConnection(rs, st, connection);
            }
        }
        return planes;
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

            Flight flight = Flight.builder().departureDate(LocalDate.parse(rs.getString(DEPARTURE_DATE)))
                            .departureTime(LocalTime.parse(rs.getString(DEPARTURE_TIME)))
                            .departureAirportShortName(rs.getString(DEP_AIRPORT_SHORT_NAME))
                            .build();

            return flight;
        }catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during searching first plane flight after date", e);
        }finally {
            if(pool != null) {
                pool.closeConnection(rs, ps, connection);
            }
        }

    }
}
