package by.epam.airport_system.dao.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.Plane;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.FlightDao;
import by.epam.airport_system.dao.connectionpool.ConnectionPool;
import by.epam.airport_system.dao.connectionpool.ConnectionPoolException;
import static by.epam.airport_system.dao.impl.DbParameterName.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class FlightDaoImpl implements FlightDao{
    private final static String FLIGHT_CREATION_STATUS = "Scheduled";
    private final static String AIRPORT_DEPARTURE = "SELECT `flight-number`, `departure-date` AS `date`, `departure-time` AS `time`," +
            "a1.`name` AS `airport`,\n" +
            "cities.`name` AS `city`, a1.`name-abbreviation` AS `airport-short-name`, title, `status`\n" +
            "FROM airport.flights\n" +
            "JOIN airport.airports AS a1 ON  a1.id = flights.`destination-airport-id`\n" +
            "JOIN airport.cities ON cities.id = (SELECT `city-id` FROM airport.airports WHERE airports.`name` = a1.`name`)\n" +
            "JOIN airport.`plane-models` ON `plane-models`.id = (SELECT `model-id` FROM airport.planes WHERE planes.id = `plane-id`)\n" +
            "JOIN airport.airports AS a2 ON a2.id = flights.`departure-airport-id`\n" +
            "WHERE `departure-date` = ? AND a2.`name-abbreviation` = ?;";

    private final static String AIRPORT_ARRIVAL = "SELECT `flight-number`, `departure-date` AS `date`, `destination-time` AS `time`," +
            "a2.`name` AS `airport`," +
            "cities.`name` AS `city`, a2.`name-abbreviation` AS `airport-short-name`, title, `status`\n" +
            "FROM airport.flights\n" +
            "JOIN airport.airports AS a2 ON a2.id = flights.`departure-airport-id`\n" +
            "JOIN airport.cities ON cities.id = (SELECT `city-id` FROM airport.airports WHERE airports.`name` = a2.`name`)\n" +
            "JOIN airport.airports AS a1 ON  a1.id = flights.`destination-airport-id`\n" +
            "JOIN airport.`plane-models` ON `plane-models`.id = (SELECT `model-id` FROM airport.planes WHERE planes.id = `plane-id`)\n" +
            "WHERE `destination-date` = ? AND a1.`name-abbreviation` = ?;";

    private final static String FLIGHT_INFO =   "SELECT flights.id AS `id`, `flight-number`, `number` AS `plane-number`, title AS `plane-model`," +
            "`status`,`destination-date`, `destination-time`, a2.`name` AS `departure-airport`," +
            "c2.`name` AS `departure-city`, cnt2.`name` AS `departure-country`,  a2.`name-abbreviation` AS `dep-airport-short-name`, \n" +
            "`departure-date`, `departure-time`, a1.`name` AS `destination-airport`, c1.`name` AS `destination-city` , " +
            "cnt1.`name` AS `destination-country`, a1.`name-abbreviation` AS `dest-airport-short-name`\n" +
            "FROM airport.flights\n" +
            "JOIN airport.`planes` ON  planes.id = `plane-id`\n" +
            "JOIN airport.`plane-models` ON `plane-models`.id = (SELECT `model-id` FROM airport.planes WHERE planes.id = `plane-id`)\n"+
            "JOIN airport.airports AS a1 ON  a1.id = flights.`destination-airport-id`\n" +
            "JOIN airport.cities AS c1 ON c1.id = (SELECT `city-id` FROM airport.airports WHERE airports.`name` = a1.`name`)\n" +
            "JOIN airport.countries AS cnt1 ON cnt1.id = c1.`country-id`\n" +
            "JOIN airport.airports AS a2 ON a2.id = flights.`departure-airport-id`\n" +
            "JOIN airport.cities AS c2 ON c2.id = (SELECT `city-id` FROM airport.airports WHERE airports.`name` = a2.`name`)\n" +
            "JOIN airport.countries AS cnt2 ON cnt2.id = c2.`country-id`\n" +
            "WHERE `flight-number` = ? AND `departure-date` = ?;\n";

    private final static String CREATE_FLIGHT = "INSERT INTO airport.flights (`flight-number`, `plane-id`, `departure-airport-id`," +
            " `destination-airport-id`, `departure-date`,\n" +
            " `destination-date`, `departure-time`, `destination-time`, `status`, `dispatcher-id`) \n" +
            " VALUES (?, (SELECT id FROM airport.planes WHERE `number` = ?),\n" +
            "(SELECT id FROM airport.airports WHERE `name-abbreviation` = ?), (SELECT id FROM airport.airports WHERE `name-abbreviation` = ?), ?, ?,\n" +
            "?, ?, ?, (SELECT id FROM airport.users WHERE `name` = ? AND surname = ?));";

    private final static String DOES_FLIGHT_NUMBER_EXIST = "SELECT `destination-date` FROM airport.flights " +
            "WHERE `flight-number` = ? AND `departure-date` = ? " +
            "OR `flight-number` = ? AND `destination-date` = ?";

    private final static String ALL_FLIGHTS_BY_DAY = "SELECT `flight-number`, title AS `plane-model`," +
            "`departure-date`, `departure-time`, `destination-date`, `destination-time`, \n" +
            "c1.`name` AS `destination-city` , a1.`name-abbreviation` AS `dest-airport-short-name`,\n" +
            "c2.`name` AS `departure-city`, a2.`name-abbreviation` AS `dep-airport-short-name`, `status`\n" +
            "FROM airport.flights\n" +
            "JOIN airport.`plane-models` ON `plane-models`.id = (SELECT `model-id` FROM airport.planes WHERE planes.id = `plane-id`)\n" +
            "JOIN airport.airports AS a1 ON  a1.id = flights.`destination-airport-id`\n" +
            "JOIN airport.cities AS c1 ON  c1.id = (SELECT `city-id` FROM airport.airports WHERE airports.`name` = a1.`name`)\n" +
            "JOIN airport.airports AS a2 ON a2.id = flights.`departure-airport-id`\n" +
            "JOIN airport.cities AS c2 ON  c2.id = (SELECT `city-id` FROM airport.airports WHERE airports.`name` = a2.`name`)" +
            "WHERE `departure-date` = ?;";

    private final static String DELETE_FLIGHT = "DELETE FROM airport.flights WHERE `flight-number` = ? AND `departure-date` = ?";

    private final static String EDIT_FLIGHT = "UPDATE airport.flights " +
            "SET `plane-id` = (SELECT id FROM airport.planes WHERE `number` = ?),\n" +
            "`flight-team-id` = (SELECT id FROM airport.`flight-teams` WHERE `short-name` = ?),\n" +
            "`departure-airport-id` = (SELECT id FROM airport.airports WHERE `name-abbreviation` = ?),\n" +
            "`destination-airport-id` = (SELECT id FROM airport.airports WHERE `name-abbreviation` = ?),\n" +
            "`departure-date` = ?, `destination-date` = ?, `departure-time` = ?,\n" +
            "`status` = ?, `destination-time` = ?\n" +
            "WHERE  id = ?";

    private final static String FIND_FLIGHT = "SELECT `flight-number`, `departure-time`, `destination-time`," +
            "`departure-date`, title AS `plane-model`\n" +
            "FROM airport.flights\n" +
            "JOIN airport.`plane-models` ON `plane-models`.id = (SELECT `model-id` FROM airport.planes WHERE id = `plane-id`)\n" +
            "WHERE `departure-airport-id` = (SELECT id FROM airport.airports WHERE `name-abbreviation` = ?)\n" +
            "AND `destination-airport-id` = (SELECT id FROM airport.airports WHERE `name-abbreviation` = ?)\n" +
            "AND `departure-date` BETWEEN adddate(current_date(), INTERVAL 1 day) AND ?";

    @Override
    public List<Flight> airportArrivals(LocalDate departureDate, String airportShortName) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;

        try {
            connection = pool.takeConnection();
            return makeFlightList(AIRPORT_ARRIVAL, departureDate, airportShortName, connection);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Exception during arrivals selecting!", e);
        }finally {
            if(pool!= null){
                pool.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Flight> airportDepartures(LocalDate departureDate, String airportShortName) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;

        try {
            connection = pool.takeConnection();
            return makeFlightList(AIRPORT_DEPARTURE, departureDate, airportShortName, connection);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Exception during arrivals selecting!", e);
        }finally {
            if(pool!= null){
                pool.releaseConnection(connection);
            }
        }
    }

    private List<Flight> makeFlightList(String query, LocalDate departureDate, String airport, Connection connection) throws SQLException {
        List <Flight> flights = new ArrayList<>();
        ResultSet rs;

        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setDate(1, Date.valueOf(departureDate));
            ps.setString(2, airport);

            rs = ps.executeQuery();
            while (rs.next()) {
                flights.add( Flight.builder().status(rs.getString(STATUS))
                        .planeModel(rs.getString(TITLE))
                        .departureDate(rs.getDate(DATE).toLocalDate())
                        .departureTime(rs.getTime(TIME).toLocalTime())
                        .destinationCity(rs.getString(CITY))
                        .destinationAirportShortName(rs.getString(AIRPORT_SHORT_NAME))
                        .flightNumber(rs.getString(FLIGHT_NUMBER)).build());
            }
        }
        return flights;
    }

    @Override
    public Flight flightInfo(String flightNumber, LocalDate departureDate) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Flight flight;

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(FLIGHT_INFO);

            ps.setString(1,flightNumber);
            ps.setDate(2,Date.valueOf(departureDate));

            rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            }
            flight = Flight.builder().id(rs.getInt(ID))
                    .flightNumber(rs.getString(FLIGHT_NUMBER))
                    .status(rs.getString(STATUS))
                    .destinationDate(rs.getDate(DESTINATION_DATE).toLocalDate())
                    .destinationTime( rs.getTime(DESTINATION_TIME).toLocalTime())
                    .destinationAirport( rs.getString(DESTINATION_AIRPORT))
                    .destinationCity(rs.getString(DESTINATION_CITY))
                    .destinationCountry(rs.getString(DESTINATION_COUNTRY))
                    .destinationAirportShortName(rs.getString(DEST_AIRPORT_SHORT_NAME))
                    .departureDate(rs.getDate(DEPARTURE_DATE).toLocalDate())
                    .departureTime(rs.getTime(DEPARTURE_TIME).toLocalTime())
                    .departureAirport( rs.getString(DEPARTURE_AIRPORT))
                    .departureCity( rs.getString(DEPARTURE_CITY))
                    .departureCountry(rs.getString(DEPARTURE_COUNTRY))
                    .departureAirportShortName( rs.getString(DEP_AIRPORT_SHORT_NAME))
                    .plane(new Plane(rs.getString(PLANE_MODEL), rs.getString(PLANE_NUMBER))).build();

        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during flight info selecting!", e);
        }finally{
            if(pool != null) {
                pool.closeConnection(rs, ps, connection);
            }
        }
        return flight;
    }

    @Override
    public int createFlight(Flight flight) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(CREATE_FLIGHT);

            ps.setString(1, flight.getFlightNumber());
            ps.setString(2, flight.getPlaneNumber());
            ps.setString(3, flight.getDepartureAirportShortName());
            ps.setString(4, flight.getDestinationAirportShortName());
            ps.setDate(5, Date.valueOf(flight.getDepartureDate()));
            ps.setDate(6, Date.valueOf(flight.getDestinationDate()));
            ps.setTime(7, Time.valueOf(flight.getDepartureTime()));
            ps.setTime(8, Time.valueOf(flight.getDestinationTime()));
            ps.setString(9, FLIGHT_CREATION_STATUS);
            ps.setString(10, flight.getDispatcher().getName());
            ps.setString(11, flight.getDispatcher().getSurname());

            return ps.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during nearest flight selecting!", e);
        }finally{
            if(pool != null) {
                pool.closeConnection(ps, connection);
            }
        }
    }

    @Override
    public boolean doesFlightNumberExist(String flightNumber, LocalDate date) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(DOES_FLIGHT_NUMBER_EXIST);
            ps.setString(1, flightNumber);
            ps.setDate(2, Date.valueOf(date));
            ps.setString(3, flightNumber);
            ps.setDate(4, Date.valueOf(date));

            rs = ps.executeQuery();

            return rs.next();
        }catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during flight number existence checking!", e);
        }finally {
            if(pool != null) {
                pool.closeConnection(rs, ps, connection);
            }
        }
    }

    @Override
    public List<Flight> allFlightByDay(LocalDate departureDate) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List <Flight> flights = new ArrayList<>();

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(ALL_FLIGHTS_BY_DAY);
            ps.setDate(1,Date.valueOf(departureDate));

            rs = ps.executeQuery();
            while (rs.next()){
                flights.add( Flight.builder().status(rs.getString(STATUS))
                        .planeModel(rs.getString(PLANE_MODEL))
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
    public int deleteFlight(String flightNumber, LocalDate departureDate) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(DELETE_FLIGHT);

            ps.setString(1, flightNumber);
            ps.setDate(2, Date.valueOf(departureDate));

            return ps.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during flight deleting!", e);
        }finally{
            if(pool != null) {
                pool.closeConnection(ps, connection);
            }
        }
    }

    @Override
    public int editFlight(Flight flight) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(EDIT_FLIGHT);

            ps.setString(1, flight.getPlaneNumber());
            ps.setString(2, flight.getCrew());
            ps.setString(3, flight.getDepartureAirportShortName());
            ps.setString(4, flight.getDestinationAirportShortName());
            ps.setDate(5, Date.valueOf(flight.getDepartureDate()));
            ps.setDate(6, Date.valueOf(flight.getDestinationDate()));
            ps.setTime(7, Time.valueOf(flight.getDepartureTime()));
            ps.setString(8,flight.getStatus());
            ps.setTime(9, Time.valueOf(flight.getDestinationTime()));
            ps.setInt(10, flight.getId());

            return ps.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during flight editing!", e);
        }finally{
            if(pool != null) {
                pool.closeConnection(ps, connection);
            }
        }
    }

    @Override
    public List<Flight> findFlight(String departureAirport, String destinationAirport, LocalDate lastDayOfRange) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List <Flight> flights = new ArrayList<>();

        try {
            connection = pool.takeConnection();

            ps =  connection.prepareStatement(FIND_FLIGHT);
            ps.setString(1, departureAirport);
            ps.setString(2, destinationAirport);
            ps.setDate(3, Date.valueOf(lastDayOfRange));

            rs = ps.executeQuery();
            while (rs.next()){
                flights.add( Flight.builder().planeModel(rs.getString(PLANE_MODEL))
                            .departureDate(rs.getDate(DEPARTURE_DATE).toLocalDate())
                            .destinationTime( rs.getTime(DESTINATION_TIME).toLocalTime())
                            .departureTime(rs.getTime(DEPARTURE_TIME).toLocalTime())
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
}