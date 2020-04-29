package by.epam.tr.dao.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.FlightDAO;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.util.ArrayList;

public class FlightDAOImpl implements FlightDAO {

    private final static String USER_FLIGHT =   "SELECT model, `short-name`, `departure-date`, `departure-time`, `destination-date`, `destination-time`, \n" +
                                                "c1.`name` AS `destination-city` , a1.`name-abbreviation` AS `dest-airport-short-name`,\n" +
                                                "c2.`name` AS `departure-city`, a2.`name-abbreviation` AS `dep-airport-short-name`, `flight-number` \n" +
                                                "FROM flights\n" +
                                                "JOIN `planes-characteristic` ON (SELECT `planes-characteristic-id` FROM planes WHERE planes.id = `plane-id` ) = `planes-characteristic`.id\n" +
                                                "JOIN `flight-teams` ON  flights.`flight-team-id` = `flight-teams`.id\n" +
                                                "JOIN `flight-teams-m2m-users` ON   `flight-teams-m2m-users`.`flight-team-id` = `flight-teams`.id \n" +
                                                "JOIN airports AS a1 ON  a1.id = flights.`destination-airport-id`\n" +
                                                "JOIN cities AS c1 ON  c1.id = (SELECT `city-id` FROM airports WHERE airports.`name` = a1.`name`)\n" +
                                                "JOIN airports AS a2 ON a2.id = flights.`departure-airport-id`\n" +
                                                "JOIN cities AS c2 ON  c2.id = (SELECT `city-id` FROM airports WHERE airports.`name` = a2.`name`)\n" +
                                                "WHERE `user-id` = (SELECT id FROM users WHERE login = ?);\n";


    private final static String ALL_FLIGHTS =   "SELECT model,`short-name`, `departure-date`, `destination-date`, `departure-time`, `destination-time`, \n" +
                                                "c1.`name` AS `destination-city`,  a1.`name-abbreviation` AS `dest-airport-short-name`,\n" +
                                                "c2.`name` AS `departure-city`, a2.`name-abbreviation` AS `dep-airport-short-name`,`flight-number`\n" +
                                                "FROM airport.flights\n" +
                                                "JOIN airport.`planes-characteristic` ON (SELECT `planes-characteristic-id` FROM planes WHERE planes.id = `plane-id` ) = `planes-characteristic`.id\n" +
                                                "JOIN airport.`flight-teams` ON flights.`flight-team-id` = `flight-teams`.id\n" +
                                                "JOIN airports AS a1 ON  a1.id = flights.`destination-airport-id` \n" +
                                                "JOIN cities AS c1 ON c1.id = (SELECT  `city-id` FROM airports WHERE airports.`name` = a1.`name`)\n" +
                                                "JOIN countries AS cnt1 ON cnt1.id = c1.`country-id`\n" +
                                                "JOIN airports AS a2 ON \ta2.id = flights.`departure-airport-id` \n" +
                                                "JOIN cities AS c2 ON c2.id = (SELECT  `city-id` FROM airports WHERE airports.`name` = a2.`name`)\n" +
                                                "JOIN countries AS cnt2 on  cnt2.id = c2.`country-id`;";

    private final static String FLIGHT_INFO =   "SELECT `destination-date`, `destination-time`, a2.`name` AS `departure-airport`, c2.`name` AS `departure-city`, cnt2.`name` AS `departure-country`,  a2.`name-abbreviation` AS `dep-airport-short-name`, \n" +
                                                "`departure-date`, `departure-time`, a1.`name` AS `destination-airport`, c1.`name` AS `destination-city` , cnt1.`name` AS `destination-country`, a1.`name-abbreviation` AS `dest-airport-short-name`\n" +
                                                "FROM flights\n" +
                                                "JOIN `planes-characteristic` ON (SELECT `planes-characteristic-id` FROM planes WHERE planes.id = `plane-id` ) = `planes-characteristic`.id\n" +
                                                "JOIN airports AS a1 ON  a1.id = flights.`destination-airport-id`\n" +
                                                "JOIN cities AS c1 ON c1.id = (SELECT `city-id` FROM airports WHERE airports.`name` = a1.`name`)\n" +
                                                "JOIN countries AS cnt1 ON cnt1.id = c1.`country-id`\n" +
                                                "JOIN airports AS a2 ON \ta2.id = flights.`departure-airport-id`\n" +
                                                "JOIN cities AS c2 ON c2.id = (SELECT `city-id` FROM airports WHERE airports.`name` = a2.`name`)\n" +
                                                "JOIN countries AS cnt2 ON cnt2.id = c2.`country-id`\n" +
                                                "WHERE `flight-number` = ? AND `departure-date` = ?;\n";
    @Override
    public ArrayList<Flight> userFlightsList(String login) throws DAOException {

        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList <Flight> flights = new ArrayList<>();

        try {

            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(USER_FLIGHT);

            ps.setString(1,login);

            rs = ps.executeQuery();

            while (rs.next()){
                flights.add(new Flight(rs.getString("model"), rs.getString("departure-date"), rs.getString("departure-time"), rs.getString("destination-date"),
                        rs.getString("destination-time"),  rs.getString("destination-city"), rs.getString("departure-city"), rs.getString("short-name"),
                        rs.getString("dest-airport-short-name"), rs.getString("dep-airport-short-name"), rs.getString("flight-number")));
            }

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during flight selecting!");
        }finally{

            if(rs !=  null){
                closeResultSet(rs);
            }

            if(ps != null){
                closeStatement(ps);
            }

            if (pool != null) {
                pool.releaseConnection(connection);
            }
        }

        return flights;
    }

    @Override
    public ArrayList<Flight> allFlightsList() throws DAOException {

        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList <Flight> flights = new ArrayList<>();

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(ALL_FLIGHTS);

            rs = ps.executeQuery();

            while (rs.next()){
                flights.add(new Flight(rs.getString("model"), rs.getString("departure-date"), rs.getString("departure-time"), rs.getString("destination-date"),
                        rs.getString("destination-time"),  rs.getString("destination-city"), rs.getString("departure-city"), rs.getString("short-name"),
                        rs.getString("dest-airport-short-name"), rs.getString("dep-airport-short-name"), rs.getString("flight-number")));
            }

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during flight selecting!");
        }finally{

            if(rs !=  null){
                closeResultSet(rs);
            }

            if(ps != null){
                closeStatement(ps);
            }

            if (pool != null) {
                pool.releaseConnection(connection);
            }
        }

        return flights;
    }

    @Override
    public Flight flightInfo(String flightNumber, String departureDate) throws DAOException {

        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Flight flight;

        try {
            pool = ConnectionPool.getInstance();
            pool.poolInitialization();
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(FLIGHT_INFO);

            ps.setString(1,flightNumber);
            ps.setString(2,departureDate);

            rs = ps.executeQuery();

            if(!rs.next()){
                return null;
            }

            flight = new Flight(rs.getString("destination-date"), rs.getString("destination-time"), rs.getString("destination-airport"), rs.getString("destination-city"),
                            rs.getString("destination-country"),  rs.getString("dest-airport-short-name"),rs.getString("departure-date"), rs.getString("departure-time"),
                            rs.getString("departure-airport"), rs.getString("departure-city"),rs.getString("departure-country"),  rs.getString("dep-airport-short-name") );


        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during flight info selecting!");
        }finally{

            if(rs !=  null){
                closeResultSet(rs);
            }

            if(ps != null){
                closeStatement(ps);
            }

            if (pool != null) {
                pool.releaseConnection(connection);
            }
        }

        return flight;
    }

    private void closeStatement(Statement st) {

        try {
            st.close();
        } catch (SQLException e) {
            //log
        }
    }

    private void closeResultSet(ResultSet rs){

        try {
            rs.close();
        } catch (SQLException e) {
            //log
        }
    }

}
