package by.epam.airport_system.dao.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.User;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.UserFlightsDao;
import by.epam.airport_system.dao.connectionpool.ConnectionPool;
import by.epam.airport_system.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserFlightsDaoImpl implements UserFlightsDao, CloseOperation {
    private final static String USER_FLIGHTS_BY_DAY =   "SELECT `status`, title, `departure-date`, `departure-time`, `destination-date`, `destination-time`, \n" +
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
            "WHERE `user-id` = ?  AND `departure-date` = ?;";

    private final static String DISPATCHER_ARRIVALS = "SELECT `status`, title, `destination-date` AS `date`, `destination-time` AS `time`, \n" +
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

    private final static String DISPATCHER_DEPARTURES = "SELECT `status`, title, `departure-date` AS `date`, `departure-time` AS `time`, \n" +
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

    private final static String LAST_USER_FLIGHT = "SELECT `name-abbreviation` AS `dest-airport-short-name`," +
            "`destination-time`, `destination-date`\n" +
            "FROM flights\n" +
            "JOIN airports ON airports.id = flights.`destination-airport-id`\n" +
            "JOIN `flight-teams` ON  flights.`flight-team-id` = `flight-teams`.id\n" +
            "JOIN `flight-teams-m2m-users` ON   `flight-teams-m2m-users`.`flight-team-id` = `flight-teams`.id \n" +
            "WHERE `user-id` = (SELECT id FROM users WHERE surname = ?  AND email = ?)\n" +
            "AND `destination-date` <= ? \n" +
            "ORDER BY `destination-date` DESC, `destination-time` DESC LIMIT 1";

    private final static String FIRST_USER_FLIGHT = "SELECT `name-abbreviation` AS `dep-airport-short-name`," +
            "`departure-time`, `departure-date`\n" +
            "FROM flights\n" +
            "JOIN airports ON  airports.id = flights.`departure-airport-id`\n" +
            "JOIN `flight-teams` ON  flights.`flight-team-id` = `flight-teams`.id\n" +
            "JOIN `flight-teams-m2m-users` ON  `flight-teams-m2m-users`.`flight-team-id` = `flight-teams`.id \n" +
            "WHERE `user-id` = (SELECT id FROM users WHERE surname = ?  AND email = ?)\n" +
            "AND `departure-date` >= ?\n" +
            "ORDER BY `departure-date`, `departure-time` LIMIT 1";

    @Override
    public List<Flight> userFlights(int id, LocalDate date) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List <Flight> flights = new ArrayList<>();

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(USER_FLIGHTS_BY_DAY);

            ps.setInt(1, id);
            ps.setDate(2, Date.valueOf(date));

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
            throw new DaoException("Exception during flight selecting!", e);
        }finally{
            closeAll(rs, ps, pool, connection);
        }
        return flights;
    }

    @Override
    public List<Flight> dispatcherFlights(String surname, String email) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        List <Flight> flights = new ArrayList<>();

        try {
            connection = pool.takeConnection();

            flights.addAll(dispatcherArrivalsDeparturesList(DISPATCHER_ARRIVALS, surname, email, connection));
            flights.addAll(dispatcherArrivalsDeparturesList(DISPATCHER_DEPARTURES, surname, email, connection));
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during dispatcher flight selecting!", e);
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
    public Flight lastUserFlightBeforeDate(User user, LocalDate date) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Flight flight = null;

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(LAST_USER_FLIGHT);

            ps.setString(1, user.getSurname());
            ps.setString(2, user.getEmail());
            ps.setDate(3, Date.valueOf(date));

            rs = ps.executeQuery();
            if(rs.next()) {
                flight = Flight.builder().destinationDate(rs.getDate("destination-date").toLocalDate())
                        .destinationTime(rs.getTime("destination-time").toLocalTime())
                        .destinationAirportShortName(rs.getString("dest-airport-short-name")).build();
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during nearest flight selecting!", e);
        }finally{
            closeAll(rs, ps, pool, connection);
        }
        return flight;
    }

    @Override
    public Flight firstUserFlightAfterDate(User user, LocalDate date) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Flight flight = null;

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(FIRST_USER_FLIGHT);

            ps.setString(1, user.getSurname());
            ps.setString(2, user.getEmail());
            ps.setDate(3, Date.valueOf(date));

            rs = ps.executeQuery();
            if(rs.next()) {
                flight = Flight.builder().departureDate(rs.getDate("departure-date").toLocalDate())
                        .departureTime(rs.getTime("departure-time").toLocalTime())
                        .departureAirportShortName(rs.getString("dep-airport-short-name")).build();
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during nearest flight selecting!", e);
        }finally{
            closeAll(rs, ps, pool, connection);
        }
        return flight;
    }
}
