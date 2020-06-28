package by.epam.tr.dao.impl;

import by.epam.tr.bean.Plane;
import by.epam.tr.dao.CloseOperation;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.PlaneDao;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaneDaoImpl extends CloseOperation implements PlaneDao{
    private final static String PLANES_AT_AIRPORT = "SELECT `number`, title \n"+
            "FROM airport.flights\n"+
            "JOIN planes ON `plane-id` = planes.id\n" +
            "JOIN `plane-models` ON `plane-models`.id = (SELECT `model-id` FROM planes WHERE planes.id = `plane-id`)\n"+
            "WHERE `status` = 'Scheduled'\n"+
            "AND `destination-airport-id` = (SELECT id FROM airports WHERE `name-abbreviation` = ?)\n"+
            "GROUP BY `plane-id`;";

    private final static String TAKEN_ON_FLIGHT_PLANES_AT_AIRPORT = "SELECT `number`, title \n" +
            "FROM airport.flights\n" +
            "JOIN planes ON `plane-id` = planes.id\n" +
            "JOIN `plane-models` ON `plane-models`.id = (SELECT `model-id` FROM planes WHERE planes.id = `plane-id`)\n" +
            "WHERE `status` = 'Scheduled'\n" +
            "AND `departure-airport-id` = (SELECT id FROM airports WHERE `name-abbreviation` = ?)\n" +
            "GROUP BY `plane-id`;";

    private final static String ALL_PLANES_NUMBERS = "SELECT `number`, title FROM airport.planes\n" +
            "JOIN `plane-models` ON `plane-models`.id = `model-id`";

    @Override
    public List<Plane> allPlanesFromAirport(String airportName) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;

        try {
            connection = pool.takeConnection();

            return makePlanesList(PLANES_AT_AIRPORT, airportName, connection);
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during getting airport planes list!", e);
        }finally {
            if (pool != null) {
                pool.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Plane> takenOnFlightPlanes(String airportName) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;

        try {
            connection = pool.takeConnection();

            return makePlanesList(TAKEN_ON_FLIGHT_PLANES_AT_AIRPORT, airportName, connection);
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during getting planes on flight list!", e);
        }finally {
            if (pool != null) {
                pool.releaseConnection(connection);
            }
        }
    }

    private List<Plane> makePlanesList(String query, String airportName, Connection connection) throws SQLException {
        List<Plane> planes = new ArrayList<>();
        ResultSet rs;

        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, airportName);

            rs = ps.executeQuery();
            while (rs.next()) {
                planes.add(new Plane(rs.getString("title"),rs.getString("number")));
            }
        }
        return planes;
    }

    public List<Plane> allPlanes() throws DAOException {
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
            throw new DAOException("Exception during getting planes on flight list!", e);
        }finally {
            closeAll(st, pool, connection);
        }
    }
}
