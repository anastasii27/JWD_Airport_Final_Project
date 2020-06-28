package by.epam.tr.dao.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.User;
import by.epam.tr.dao.CloseOperation;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.UserFlightsDao;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserFlightsDaoImpl extends CloseOperation implements UserFlightsDao {
    private final static String SELECT_USER_FLIGHT =   "SELECT `status`, title, `departure-date`, `departure-time`, `destination-date`, `destination-time`, \n" +
                                        "c1.`name` AS `destination-city` , a1.`name-abbreviation` AS `dest-airport-short-name`,\n" +
                                        "c2.`name` AS `departure-city`, a2.`name-abbreviation` AS `dep-airport-short-name`, `flight-number` \n" +
                                        "FROM flights\n" +
                                        "JOIN `plane-models` ON `plane-models`.id = (SELECT `model-id` FROM planes WHERE planes.id = `plane-id`)\n" +
                                        "JOIN `flight-teams` ON  flights.`flight-team-id` = `flight-teams`.id\n" +
                                        "JOIN `flight-teams-m2m-users` ON   `flight-teams-m2m-users`.`flight-team-id` = `flight-teams`.id \n" +
                                        "JOIN airports AS a1 ON  a1.id = flights.`destination-airport-id`\n" +
                                        "JOIN cities AS c1 ON  c1.id = (SELECT `city-id` FROM airports WHERE airports.`name` = a1.`name`)\n" +
                                        "JOIN airports AS a2 ON a2.id = flights.`departure-airport-id`\n" +
                                        "JOIN cities AS c2 ON  c2.id = (SELECT `city-id` FROM airports WHERE airports.`name` = a2.`name`)\n" +
                                        "WHERE `user-id` = (SELECT id FROM users WHERE surname = ? AND email=?)  AND `departure-date` = ?;";

    private final static String SELECT_NEAREST_FLIGHTS =  "SELECT `departure-date`, `departure-time`, `flight-number`,cities.`name` AS 'destination-city' FROM flights \n" +
                                        "JOIN airports AS a1 ON  a1.id = flights.`destination-airport-id`\n" +
                                        "JOIN cities  ON cities.id = (SELECT `city-id` FROM airports WHERE airports.`name` = a1.`name`)\n" +
                                        "JOIN `flight-teams` ON  flights.`flight-team-id` = `flight-teams`.id\n" +
                                        "JOIN `flight-teams-m2m-users` ON   `flight-teams-m2m-users`.`flight-team-id` = `flight-teams`.id \n" +
                                        "WHERE `user-id` = (SELECT id FROM users WHERE surname = ? AND email=?) AND `departure-date` BETWEEN  current_date() AND ? LIMIT 3\n";

    private final static String SELECT_DISPATCHER_ARRIVALS = "SELECT `status`, title, `destination-date` AS `date`, `destination-time` AS `time`, \n" +
                                        "c1.`name` AS `destination-city` , a1.`name-abbreviation` AS `dest-airport-short-name`,\n" +
                                        "c2.`name` AS `departure-city`, a2.`name-abbreviation` AS `dep-airport-short-name`, `flight-number` \n" +
                                        "FROM flights\n" +
                                        "JOIN `plane-models` ON `plane-models`.id = (SELECT `model-id` FROM planes WHERE planes.id = `plane-id`)\n" +
                                        "JOIN airports AS a1 ON  a1.id = flights.`destination-airport-id` \n" +
                                        "JOIN cities AS c1 ON c1.id = (SELECT  `city-id` FROM airports WHERE airports.`name` = a1.`name`)\n" +
                                        "JOIN airports AS a2 ON a2.id = flights.`departure-airport-id` \n" +
                                        "JOIN cities AS c2 ON c2.id = (SELECT  `city-id` FROM airports WHERE airports.`name` = a2.`name`)\n" +
                                        "WHERE `dispatcher-id` = (SELECT id FROM users WHERE surname= ? AND email= ?)\n" +
                                        "AND `destination-date` BETWEEN current_date() AND date_add(current_date(),interval 1 day)\n" +
                                        "AND c1.`name`= 'Minsk';";

    private final static String SELECT_DISPATCHER_DEPARTURES = "SELECT `status`, title, `departure-date` AS `date`, `departure-time` AS `time`, \n" +
                                        "c1.`name` AS `destination-city` , a1.`name-abbreviation` AS `dest-airport-short-name`,\n" +
                                        "c2.`name` AS `departure-city`, a2.`name-abbreviation` AS `dep-airport-short-name`, `flight-number` \n" +
                                        "FROM flights\n" +
                                        "JOIN `plane-models` ON `plane-models`.id = (SELECT `model-id` FROM planes WHERE planes.id = `plane-id`)\n" +
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

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SELECT_USER_FLIGHT);

            ps.setString(1,params.get("surname"));
            ps.setString(2,params.get("email"));
            ps.setDate(3, Date.valueOf(params.get("departure_date")));

            rs = ps.executeQuery();
            while (rs.next()){
                flights.add( Flight.builder().status(rs.getString("status"))
                        .planeModel(rs.getString("title"))
                        .departureDate(rs.getDate("departure-date").toLocalDate())
                        .departureTime(rs.getTime("departure-time").toLocalTime())
                        .destinationDate(rs.getDate("destination-date").toLocalDate())
                        .destinationTime( rs.getTime("destination-time").toLocalTime())
                        .destinationCity(rs.getString("destination-city"))
                        .departureCity( rs.getString("departure-city"))
                        .destinationAirportShortName(rs.getString("dest-airport-short-name"))
                        .departureAirportShortName( rs.getString("dep-airport-short-name"))
                        .flightNumber(rs.getString("flight-number")).build());
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during flight selecting!", e);
        }finally{
            closeAll(rs, ps, pool, connection);
        }
        return flights;
    }

    @Override
    public List<Flight> nearestUserFlights(String surname, String email, LocalDate lastDayOfRange) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List <Flight> flights = new ArrayList<>();

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SELECT_NEAREST_FLIGHTS);

            ps.setString(1, surname);
            ps.setString(2, email);
            ps.setDate(3, Date.valueOf(lastDayOfRange));

            rs = ps.executeQuery();
            while (rs.next()){
                flights.add( Flight.builder().departureDate(rs.getDate("departure-date").toLocalDate())
                        .departureTime(rs.getTime("departure-time").toLocalTime())
                        .destinationCity(rs.getString("destination-city"))
                        .flightNumber(rs.getString("flight-number")).build());
            }

        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during nearest flight selecting!", e);
        }finally{
            closeAll(rs, ps, pool, connection);
        }
        return flights;
    }

    @Override
    public List<Flight> dispatcherFlights(String surname, String email) throws DAOException {
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
                flights.add(Flight.builder().status(rs.getString("status"))
                                            .planeModel(rs.getString("title"))
                                            .departureDate(rs.getDate("date").toLocalDate())
                                            .departureTime(rs.getTime("time").toLocalTime())
                                            .destinationCity(rs.getString("destination-city"))
                                            .departureCity( rs.getString("departure-city"))
                                            .destinationAirportShortName(rs.getString("dest-airport-short-name"))
                                            .departureAirportShortName( rs.getString("dep-airport-short-name"))
                                            .flightNumber(rs.getString("flight-number")).build());
            }
        }
        return flights;
    }

    @Override
    public List<User> freeDispatchers(LocalDate date, LocalTime time) {
        return null;
    }
}
