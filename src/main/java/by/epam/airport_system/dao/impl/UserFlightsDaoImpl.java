package by.epam.airport_system.dao.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.User;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.UserFlightsDao;
import by.epam.airport_system.dao.connectionpool.ConnectionPool;
import by.epam.airport_system.dao.connectionpool.ConnectionPoolException;
import static by.epam.airport_system.dao.impl.DbParameterName.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserFlightsDaoImpl implements UserFlightsDao{
    private final static String USER_FLIGHTS_BY_DAY =   "SELECT `status`, title, `departure-date`, `departure-time`, `destination-date`, `destination-time`, \n" +
            "c1.`name` AS `destination-city` , a1.`name-abbreviation` AS `dest-airport-short-name`,\n" +
            "c2.`name` AS `departure-city`, a2.`name-abbreviation` AS `dep-airport-short-name`, `flight-number` \n" +
            "FROM airport.flights\n" +
            "JOIN airport.`plane-models` ON `plane-models`.id = (SELECT `model-id` FROM airport.planes WHERE planes.id = `plane-id`)\n" +
            "JOIN airport.`flight-teams` ON  flights.`flight-team-id` = `flight-teams`.id\n" +
            "JOIN airport.`flight-teams-m2m-users` ON   `flight-teams-m2m-users`.`flight-team-id` = `flight-teams`.id \n" +
            "JOIN airport.airports AS a1 ON  a1.id = flights.`destination-airport-id`\n" +
            "JOIN airport.cities AS c1 ON  c1.id = (SELECT `city-id` FROM airport.airports WHERE airports.`name` = a1.`name`)\n" +
            "JOIN airport.airports AS a2 ON a2.id = flights.`departure-airport-id`\n" +
            "JOIN airport.cities AS c2 ON  c2.id = (SELECT `city-id` FROM airport.airports WHERE airports.`name` = a2.`name`)\n" +
            "WHERE `user-id` = ?  AND `departure-date` = ?;";

    private final static String DISPATCHER_ARRIVALS = "SELECT `status`, title, `destination-date` AS `date`, `destination-time` AS `time`, \n" +
            "c1.`name` AS `destination-city` , a1.`name-abbreviation` AS `dest-airport-short-name`,\n" +
            "c2.`name` AS `departure-city`, a2.`name-abbreviation` AS `dep-airport-short-name`, `flight-number` \n" +
            "FROM airport.flights\n" +
            "JOIN airport.`plane-models` ON `plane-models`.id = (SELECT `model-id` FROM airport.planes WHERE planes.id = `plane-id`)\n" +
            "JOIN airport.airports AS a1 ON  a1.id = flights.`destination-airport-id` \n" +
            "JOIN airport.cities AS c1 ON c1.id = (SELECT  `city-id` FROM airport.airports WHERE airports.`name` = a1.`name`)\n" +
            "JOIN airport.airports AS a2 ON a2.id = flights.`departure-airport-id` \n" +
            "JOIN airport.cities AS c2 ON c2.id = (SELECT  `city-id` FROM airport.airports WHERE airports.`name` = a2.`name`)\n" +
            "WHERE `dispatcher-id` = (SELECT id FROM airport.users WHERE surname= ? AND email= ?)\n" +
            "AND `destination-date` BETWEEN current_date() AND date_add(current_date(),interval 1 day)\n" +
            "AND c1.`name`= 'Minsk';";

    private final static String DISPATCHER_DEPARTURES = "SELECT `status`, title, `departure-date` AS `date`, `departure-time` AS `time`, \n" +
            "c1.`name` AS `destination-city` , a1.`name-abbreviation` AS `dest-airport-short-name`,\n" +
            "c2.`name` AS `departure-city`, a2.`name-abbreviation` AS `dep-airport-short-name`, `flight-number` \n" +
            "FROM airport.flights\n" +
            "JOIN airport.`plane-models` ON `plane-models`.id = (SELECT `model-id` FROM airport.planes WHERE planes.id = `plane-id`)\n" +
            "JOIN airport.airports AS a1 ON  a1.id = flights.`destination-airport-id` \n" +
            "JOIN airport.cities AS c1 ON c1.id = (SELECT  `city-id` FROM airport.airports WHERE airports.`name` = a1.`name`)\n" +
            "JOIN airport.airports AS a2 ON a2.id = flights.`departure-airport-id` \n" +
            "JOIN airport.cities AS c2 ON c2.id = (SELECT  `city-id` FROM airport.airports WHERE airports.`name` = a2.`name`)\n" +
            "WHERE `dispatcher-id` = (SELECT id FROM airport.users WHERE surname= ? AND email= ?)\n" +
            "AND `departure-date` BETWEEN current_date() AND date_add(current_date(), interval 1 day)\n" +
            "AND c2.`name`= 'Minsk';";

    private final static String LAST_USER_FLIGHT = "SELECT `name-abbreviation` AS `dest-airport-short-name`," +
            "`destination-time`, `destination-date`\n" +
            "FROM airport.flights\n" +
            "JOIN airport.airports ON airports.id = flights.`destination-airport-id`\n" +
            "JOIN airport.`flight-teams` ON  flights.`flight-team-id` = `flight-teams`.id\n" +
            "JOIN airport.`flight-teams-m2m-users` ON   `flight-teams-m2m-users`.`flight-team-id` = `flight-teams`.id \n" +
            "WHERE `user-id` = (SELECT id FROM airport.users WHERE surname = ?  AND email = ?)\n" +
            "AND `destination-date` <= ? \n" +
            "ORDER BY `destination-date` DESC, `destination-time` DESC LIMIT 1";

    private final static String FIRST_USER_FLIGHT = "SELECT `name-abbreviation` AS `dep-airport-short-name`," +
            "`departure-time`, `departure-date`\n" +
            "FROM airport.flights\n" +
            "JOIN airport.airports ON  airports.id = flights.`departure-airport-id`\n" +
            "JOIN airport.`flight-teams` ON  flights.`flight-team-id` = `flight-teams`.id\n" +
            "JOIN airport.`flight-teams-m2m-users` ON  `flight-teams-m2m-users`.`flight-team-id` = `flight-teams`.id \n" +
            "WHERE `user-id` = (SELECT id FROM airport.users WHERE surname = ?  AND email = ?)\n" +
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
                flights.add( Flight.builder().status(rs.getString(STATUS))
                        .planeModel(rs.getString(TITLE))
                        .departureDate(rs.getDate(DEPARTURE_DATE).toLocalDate())
                        .departureTime(rs.getTime(DEPARTURE_TIME).toLocalTime())
                        .destinationDate(rs.getDate(DESTINATION_DATE).toLocalDate())
                        .destinationTime( rs.getTime(DESTINATION_TIME).toLocalTime())
                        .destinationCity(rs.getString(DESTINATION_CITY))
                        .departureCity( rs.getString(DEPARTURE_CITY))
                        .destinationAirportShortName(rs.getString(DEST_AIRPORT_SHORT_NAME))
                        .departureAirportShortName( rs.getString(DEP_AIRPORT_SHORT_NAME))
                        .flightNumber(rs.getString(FLIGHT_NUMBER)).build());
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during flight selecting!", e);
        }finally{
            if(pool != null) {
                pool.closeConnection(rs, ps, connection);
            }
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
                flights.add(Flight.builder().status(rs.getString(STATUS))
                                            .planeModel(rs.getString(TITLE))
                                            .departureDate(rs.getDate(DATE).toLocalDate())
                                            .departureTime(rs.getTime(TIME).toLocalTime())
                                            .destinationCity(rs.getString(DESTINATION_CITY))
                                            .departureCity( rs.getString(DEPARTURE_CITY))
                                            .destinationAirportShortName(rs.getString(DEST_AIRPORT_SHORT_NAME))
                                            .departureAirportShortName( rs.getString(DEP_AIRPORT_SHORT_NAME))
                                            .flightNumber(rs.getString(FLIGHT_NUMBER)).build());
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
                flight = Flight.builder().destinationDate(rs.getDate(DESTINATION_DATE).toLocalDate())
                        .destinationTime(rs.getTime(DESTINATION_TIME).toLocalTime())
                        .destinationAirportShortName(rs.getString(DEST_AIRPORT_SHORT_NAME)).build();
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during nearest flight selecting!", e);
        }finally{
            if(pool != null) {
                pool.closeConnection(rs, ps, connection);
            }
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
                flight = Flight.builder().departureDate(rs.getDate(DEPARTURE_DATE).toLocalDate())
                        .departureTime(rs.getTime(DEPARTURE_TIME).toLocalTime())
                        .departureAirportShortName(rs.getString(DEP_AIRPORT_SHORT_NAME)).build();
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during nearest flight selecting!", e);
        }finally{
            if(pool != null) {
                pool.closeConnection(rs, ps, connection);
            }
        }
        return flight;
    }
}
