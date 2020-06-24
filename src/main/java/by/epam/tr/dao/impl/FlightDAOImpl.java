package by.epam.tr.dao.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.dao.CloseOperation;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.FlightDAO;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class FlightDAOImpl extends CloseOperation implements FlightDAO {

    private final static String ARRIVAL = "arrival";
    private final static String DEPARTURE = "departure";

    private final static String SELECT_USER_FLIGHT =   "SELECT `status`, model, `departure-date`, `departure-time`, `destination-date`, `destination-time`, \n" +
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
                                                "WHERE `user-id` = (SELECT id FROM users WHERE surname = ? AND email=?)  AND `departure-date` = ?;\n";

    private final static String SELECT_AIRPORT_DEPARTURE = "SELECT `flight-number`, `departure-date` AS `date`, `departure-time` AS `time`, a1.`name` AS `airport`,\n" +
                                                "cities.`name` AS `city`, a1.`name-abbreviation` AS `airport-short-name`, model, `status`\n" +
                                                "FROM flights\n" +
                                                "JOIN airports AS a1 ON  a1.id = flights.`destination-airport-id`\n" +
                                                "JOIN cities ON cities.id = (SELECT `city-id` FROM airports WHERE airports.`name` = a1.`name`)\n" +
                                                "JOIN `planes-characteristic` ON (SELECT `planes-characteristic-id` FROM planes WHERE planes.id = `plane-id` ) = `planes-characteristic`.id\n" +
                                                "JOIN airports AS a2 ON a2.id = flights.`departure-airport-id`\n" +
                                                "WHERE `departure-date` = ? AND a2.`name-abbreviation` = ?;";

    private final static String SELECT_AIRPORT_ARRIVAL = "SELECT `flight-number`, `departure-date` AS `date`, `destination-time` AS `time`,cities.`name` AS `city`,\n" +
                                                "a2.`name-abbreviation` AS `airport-short-name`, model, `status`\n" +
                                                "FROM flights\n" +
                                                "JOIN airports AS a2 ON \ta2.id = flights.`departure-airport-id`\n" +
                                                "JOIN cities ON cities.id = (SELECT `city-id` FROM airports WHERE airports.`name` = a2.`name`)\n" +
                                                "JOIN airports AS a1 ON  a1.id = flights.`destination-airport-id`\n" +
                                                "JOIN `planes-characteristic` ON (SELECT `planes-characteristic-id` FROM planes WHERE planes.id = `plane-id` ) = `planes-characteristic`.id\n" +
                                                "WHERE `destination-date` = ? AND a1.`name-abbreviation` = ?;";

    private final static String SELECT_FLIGHT_INFO =   "SELECT `status`,`destination-date`, `destination-time`, a2.`name` AS `departure-airport`, c2.`name` AS `departure-city`, cnt2.`name` AS `departure-country`,  a2.`name-abbreviation` AS `dep-airport-short-name`, \n" +
                                                "`departure-date`, `departure-time`, a1.`name` AS `destination-airport`, c1.`name` AS `destination-city` , cnt1.`name` AS `destination-country`, a1.`name-abbreviation` AS `dest-airport-short-name`\n" +
                                                "FROM flights\n" +
                                                "JOIN airports AS a1 ON  a1.id = flights.`destination-airport-id`\n" +
                                                "JOIN cities AS c1 ON c1.id = (SELECT `city-id` FROM airports WHERE airports.`name` = a1.`name`)\n" +
                                                "JOIN countries AS cnt1 ON cnt1.id = c1.`country-id`\n" +
                                                "JOIN airports AS a2 ON \ta2.id = flights.`departure-airport-id`\n" +
                                                "JOIN cities AS c2 ON c2.id = (SELECT `city-id` FROM airports WHERE airports.`name` = a2.`name`)\n" +
                                                "JOIN countries AS cnt2 ON cnt2.id = c2.`country-id`\n" +
                                                "WHERE `flight-number` = ? AND `departure-date` = ?;\n";

    private final static String SELECT_NEAREST_FLIGHTS =  "SELECT `departure-date`, `departure-time`, `flight-number`,cities.`name` AS 'destination-city' FROM flights \n" +
                                                "JOIN airports AS a1 ON  a1.id = flights.`destination-airport-id`\n" +
                                                "JOIN cities  ON cities.id = (SELECT `city-id` FROM airports WHERE airports.`name` = a1.`name`)\n" +
                                                "JOIN `flight-teams` ON  flights.`flight-team-id` = `flight-teams`.id\n" +
                                                "JOIN `flight-teams-m2m-users` ON   `flight-teams-m2m-users`.`flight-team-id` = `flight-teams`.id \n" +
                                                "WHERE `user-id` = (SELECT id FROM users WHERE surname = ? AND email=?) AND `departure-date` BETWEEN  current_date() AND ? LIMIT 3\n";

    private final static String SELECT_DISPATCHER_ARRIVALS = "SELECT `status`, model, `destination-date` AS `date`, `destination-time` AS `time`, \n" +
                                                "c1.`name` AS `destination-city` , a1.`name-abbreviation` AS `dest-airport-short-name`,\n" +
                                                "c2.`name` AS `departure-city`, a2.`name-abbreviation` AS `dep-airport-short-name`, `flight-number` \n" +
                                                "FROM flights\n" +
                                                "JOIN airport.`planes-characteristic` ON (SELECT `planes-characteristic-id` FROM planes WHERE planes.id = `plane-id` ) = `planes-characteristic`.id\n" +
                                                "JOIN airports AS a1 ON  a1.id = flights.`destination-airport-id` \n" +
                                                "JOIN cities AS c1 ON c1.id = (SELECT  `city-id` FROM airports WHERE airports.`name` = a1.`name`)\n" +
                                                "JOIN airports AS a2 ON a2.id = flights.`departure-airport-id` \n" +
                                                "JOIN cities AS c2 ON c2.id = (SELECT  `city-id` FROM airports WHERE airports.`name` = a2.`name`)\n" +
                                                "WHERE `dispatcher-id` = (SELECT id FROM users WHERE surname= ? AND email= ?)\n" +
                                                "AND `destination-date` BETWEEN current_date() AND date_add(current_date(),interval 1 day)\n" +
                                                "AND c1.`name`= 'Minsk';";

    private final static String SELECT_DISPATCHER_DEPARTURES = "SELECT `status`, model, `departure-date` AS `date`, `departure-time` AS `time`, \n" +
                                                "c1.`name` AS `destination-city` , a1.`name-abbreviation` AS `dest-airport-short-name`,\n" +
                                                "c2.`name` AS `departure-city`, a2.`name-abbreviation` AS `dep-airport-short-name`, `flight-number` \n" +
                                                "FROM flights\n" +
                                                "JOIN airport.`planes-characteristic` ON (SELECT `planes-characteristic-id` FROM planes WHERE planes.id = `plane-id` ) = `planes-characteristic`.id\n" +
                                                "JOIN airports AS a1 ON  a1.id = flights.`destination-airport-id` \n" +
                                                "JOIN cities AS c1 ON c1.id = (SELECT  `city-id` FROM airports WHERE airports.`name` = a1.`name`)\n" +
                                                "JOIN airports AS a2 ON a2.id = flights.`departure-airport-id` \n" +
                                                "JOIN cities AS c2 ON c2.id = (SELECT  `city-id` FROM airports WHERE airports.`name` = a2.`name`)\n" +
                                                "WHERE `dispatcher-id` = (SELECT id FROM users WHERE surname= ? AND email= ?)\n" +
                                                "AND `departure-date` BETWEEN current_date() AND date_add(current_date(), interval 1 day)\n" +
                                                "AND c2.`name`= 'Minsk';";

    @Override
    public List<Flight> userFlights(Map<String, String> params) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List <Flight> flights = new ArrayList<>();
        Date date = Date.valueOf(params.get("departure_date"));
        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SELECT_USER_FLIGHT);

            ps.setString(1,params.get("surname"));
            ps.setString(2,params.get("email"));
            ps.setDate(3, date);

            rs = ps.executeQuery();
            while (rs.next()){
                flights.add(new Flight(rs.getString("status"), rs.getString("model"), rs.getDate("departure-date").toLocalDate(),
                        rs.getTime("departure-time").toLocalTime(), rs.getDate("destination-date").toLocalDate(),
                        rs.getTime("destination-time").toLocalTime(), rs.getString("destination-city"),
                        rs.getString("departure-city"), rs.getString("dest-airport-short-name"),
                        rs.getString("dep-airport-short-name"), rs.getString("flight-number")));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during flight selecting!", e);
        }finally{
            closeAll(rs, ps, pool, connection);
        }
        return flights;
    }

    @Override
    public List<Flight> flightsByDay(Map<String, String> params) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List <Flight> flights = new ArrayList<>();
        Date date = Date.valueOf(params.get("departure_date"));
        String query;
        try {
            connection = pool.takeConnection();
            query = dbQueryByFlightType(params.get("type"));

            if(query!=null) {
                ps = connection.prepareStatement(query);

                ps.setDate(1, date);
                ps.setString(2, params.get("airport_short_name"));

                rs = ps.executeQuery();
                while (rs.next()) {
                    flights.add(new Flight(rs.getString("status"), rs.getString("model"),
                            rs.getDate("date").toLocalDate(), rs.getTime("time").toLocalTime(),
                            rs.getString("city"), rs.getString("airport-short-name"),
                            rs.getString("flight-number")));
                }
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during departures/arrivals selecting!", e);
        }finally{
            closeAll(rs, ps, pool, connection);
        }
        return flights;
    }

    @Override
    public Flight flightInfo(String flightNumber, String departureDate) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Flight flight;
        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SELECT_FLIGHT_INFO);

            ps.setString(1,flightNumber);
            ps.setString(2,departureDate);

            rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            }
            flight = new Flight(rs.getString("status"),rs.getDate("destination-date").toLocalDate(),
                    rs.getTime("destination-time").toLocalTime(), rs.getString("destination-airport"),
                    rs.getString("destination-city"), rs.getString("destination-country"),
                    rs.getString("dest-airport-short-name"),rs.getDate("departure-date").toLocalDate(),
                    rs.getTime("departure-time").toLocalTime(), rs.getString("departure-airport"),
                    rs.getString("departure-city"),rs.getString("departure-country"), rs.getString("dep-airport-short-name") );
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during flight info selecting!", e);
        }finally{
            closeAll(rs, ps, pool, connection);
        }
        return flight;
    }

    @Override
    public List<Flight> nearestUserFlights(String surname, String email, LocalDate lastDayOfRange) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List <Flight> flights = new ArrayList<>();
        Date date = Date.valueOf(lastDayOfRange);
        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SELECT_NEAREST_FLIGHTS);

            ps.setString(1,surname);
            ps.setString(2,email);
            ps.setDate(3,date);

            rs = ps.executeQuery();

            while (rs.next()){
                flights.add(new Flight(rs.getDate("departure-date").toLocalDate(),
                        rs.getTime("departure-time").toLocalTime(),
                        rs.getString("destination-city"),rs.getString("flight-number")));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during nearest flight selecting!", e);
        }finally{
            closeAll(rs, ps, pool, connection);
        }
        return flights;
    }

    @Override
    public List<Flight> dispatcherFlight(String surname, String email) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        List <Flight> flights = new ArrayList<>();
        try {
            connection = pool.takeConnection();

            flights.addAll(dispatcherArrivalsDeparturesList(SELECT_DISPATCHER_ARRIVALS, surname, email, connection));
            flights.addAll(dispatcherArrivalsDeparturesList(SELECT_DISPATCHER_DEPARTURES, surname, email, connection));
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during dispatcher flight selecting!", e);
        }finally{
            if(pool!=null){
                pool.releaseConnection(connection);
            }
        }
        return flights;
    }

    private List<Flight> dispatcherArrivalsDeparturesList(String dbQuery, String surname, String email, Connection connection) throws SQLException {
        ResultSet rs;
        List <Flight> flights = new ArrayList<>();
        try(PreparedStatement ps =  connection.prepareStatement(dbQuery)){
            ps.setString(1,surname);
            ps.setString(2,email);

            rs = ps.executeQuery();
            while (rs.next()){
                flights.add(new Flight(rs.getString("status"), rs.getString("model"), rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime(), rs.getString("departure-city"),
                        rs.getString("dep-airport-short-name"), rs.getString("destination-city"),
                        rs.getString("dest-airport-short-name"), rs.getString("flight-number")));
            }
        }
        return flights;
    }

    private String dbQueryByFlightType(String flightsType) {//todo заменить
        if (flightsType.equals(ARRIVAL)) {
            return SELECT_AIRPORT_ARRIVAL;
        }

        if (flightsType.equals(DEPARTURE)) {
            return SELECT_AIRPORT_DEPARTURE;
        }
        return null;
    }
}