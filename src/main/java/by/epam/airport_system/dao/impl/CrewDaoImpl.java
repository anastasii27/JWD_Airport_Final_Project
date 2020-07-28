package by.epam.airport_system.dao.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.User;
import by.epam.airport_system.dao.CrewDao;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.connectionpool.ConnectionPool;
import by.epam.airport_system.dao.connectionpool.ConnectionPoolException;
import lombok.extern.log4j.Log4j2;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
public class CrewDaoImpl implements CrewDao, CloseOperation {
    private final static String CREATE_CREW = "INSERT INTO airport.`flight-teams`(`date-of-creating`, `short-name`)\n" +
            "VALUES (current_date(), ?);";

    private final static String ADD_MEMBER = "INSERT INTO airport.`flight-teams-m2m-users`(`flight-team-id`, `user-id`) VALUES (\n" +
            "(SELECT id FROM airport.`flight-teams` WHERE `short-name` =?),\n" +
            "(SELECT id FROM airport.users WHERE `name`=? AND surname =?));";

    private final static String SET_MAIN_PILOT = "UPDATE airport.`flight-teams` \n" +
            "SET `main-pilot-id` = (SELECT id FROM airport.users WHERE `name` = ? AND `surname` = ?)\n" +
            "WHERE `short-name` = ?; ";

    private final static String CHECK_CREW_NAME_EXISTENCE = "SELECT `date-of-creating` FROM airport.`flight-teams` WHERE `short-name`=?;";

    private final static String DELETE_CREW_WITH_MEMBERS = "DELETE FROM airport.`flight-teams-m2m-users` WHERE `flight-team-id` =" +
            "(SELECT id FROM airport.`flight-teams` WHERE `short-name` = ?)";

    private final static String DELETE_CREW = "DELETE FROM airport.`flight-teams` WHERE `short-name` = ?";

    private final static String DELETE_CREW_FROM_FLIGHT = "UPDATE airport.flights\n" +
            "SET `flight-team-id` =  null WHERE `flight-team-id` = (SELECT id FROM airport.`flight-teams` " +
            "WHERE `short-name`= ?);";

    private final static String FIND_MAIN_PILOT = "SELECT `name`, surname FROM airport.`flight-teams`\n" +
            "JOIN airport.users ON `main-pilot-id`  = users.id\n" +
            "WHERE `short-name` = ?;";

    private final static String ALL_CREWS = "SELECT `short-name` FROM airport.`flight-teams`;";

    private final static String SET_CREW_FOR_FLIGHT = "UPDATE airport.flights SET `flight-team-id` =" +
            "(SELECT id FROM airport.`flight-teams` WHERE `short-name` = ?)\n" +
            "WHERE `flight-number` = ?";

    private final static String FLIGHT_CREW = "SELECT `short-name` FROM airport.flights\n" +
            "JOIN airport.`flight-teams` ON `flight-team-id` = `flight-teams`.id\n" +
            "WHERE `departure-date` = ? AND `flight-number` = ?;";

    private final static String TAKEN_ON_FLIGHTS_CREWS = "SELECT DISTINCT `short-name` \n" +
            "FROM airport.flights\n" +
            "JOIN airport.`flight-teams` ON `flight-team-id` = `flight-teams`.id;";

    @Override
    public boolean createCrew(String crewName, Map<String, User> users) throws DaoException {
        boolean flag;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;

        try{
            connection = pool.takeConnection();
            connection.setAutoCommit(false);

            createCrew(crewName, connection);
            addCrewUser(crewName, users, connection);
            setMainPilot(crewName, users.get("first_pilot"), connection);

            connection.commit();
            flag = true;
        } catch (ConnectionPoolException| SQLException e) {
            try {
                if(connection != null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                log.error("Exception while executing rollback()", ex);
            }
            throw new DaoException("Exception during crew creation", e);
        } finally {
            try {
                if(connection!=null){
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                log.error("Exception while setting auto committing = true", e);
            }
            if (pool != null) {
                pool.releaseConnection(connection);
            }
        }
        return flag;
    }

    private void addCrewUser(String crewName, Map<String, User> users, Connection connection) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(ADD_MEMBER)){
            for (Map.Entry<String, User> user : users.entrySet()) {
                ps.setString(1, crewName);
                ps.setString(2, user.getValue().getName());
                ps.setString(3, user.getValue().getSurname());
                ps.executeUpdate();
            }
        }
    }

    private int createCrew(String crewName, Connection connection) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(CREATE_CREW)){
            ps.setString(1, crewName);
            return ps.executeUpdate();
        }
    }

    private void setMainPilot(String crewName, User user, Connection connection) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(SET_MAIN_PILOT)){
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, crewName);
            ps.executeUpdate();
        }
    }

    @Override
    public boolean doesCrewNameExist(String crewName) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection  connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            connection = pool.takeConnection();

            ps = connection.prepareStatement(CHECK_CREW_NAME_EXISTENCE);
            ps.setString(1, crewName);
            rs = ps.executeQuery();

            if (!rs.next()) {
                return false;
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during crew existence checking!", e);
        } finally {
            closeAll(rs, ps, pool, connection);
        }
        return true;
    }

    @Override
    public int deleteCrew(String crewName) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection  connection = null;
        int changedRowsAmount;

        try{
            connection = pool.takeConnection();
            connection.setAutoCommit(false);

            deleteCrewWithMembers(crewName, connection);
            deleteCrewFromFlight(crewName, connection);
            changedRowsAmount = deleteCrew(crewName, connection);

            connection.commit();
        } catch (ConnectionPoolException | SQLException e) {
            try {
                if(connection != null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                log.error("Exception while executing rollback()", ex);
            }
            throw new DaoException("Exception during taking connection!",e);
        }finally {
            try {
                if(connection!=null){
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                log.error("Exception while setting auto committing = true", e);
            }
            if (pool != null) {
                pool.releaseConnection(connection);
            }
        }
        return changedRowsAmount;
    }

    private int deleteCrew(String crewName, Connection connection) throws SQLException {
        try (PreparedStatement deleteCrew = connection.prepareStatement(DELETE_CREW)) {
            deleteCrew.setString(1, crewName);
            return deleteCrew.executeUpdate();
        }
    }

    private int deleteCrewFromFlight(String crewName, Connection connection) throws SQLException {
        try(PreparedStatement deleteFromFlight = connection.prepareStatement(DELETE_CREW_FROM_FLIGHT)){
            deleteFromFlight.setString(1, crewName);
            return deleteFromFlight.executeUpdate();
        }
    }

    private int deleteCrewWithMembers(String crewName, Connection connection) throws SQLException {
        try(PreparedStatement deleteWithMembers = connection.prepareStatement(DELETE_CREW_WITH_MEMBERS)){
            deleteWithMembers.setString(1, crewName);
            return deleteWithMembers.executeUpdate();
        }
    }

    @Override
    public User findMainPilot(String crewName) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection  connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user;

        try{
            connection = pool.takeConnection();

            ps = connection.prepareStatement(FIND_MAIN_PILOT);
            ps.setString(1, crewName);
            rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }
            user = User.builder().name(rs.getString("name")).surname(rs.getString("surname")).build();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during main pilot searching!", e);
        } finally {
            closeAll(rs, ps, pool, connection);
        }
        return user;
    }

    @Override
    public List<String> allCrews() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        List<String> countries = new ArrayList<>();

        try {
            connection = pool.takeConnection();
            st = connection.createStatement();

            rs = st.executeQuery(ALL_CREWS);
            while (rs.next()) {
                countries.add(rs.getString("short-name"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during all crews getting!", e);
        } finally {
            closeAll(rs, st, pool, connection);
        }
        return countries;
    }

    @Override
    public int setCrewForFlight(String crewName, String flightNumber) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = pool.takeConnection();

            ps = connection.prepareStatement(SET_CREW_FOR_FLIGHT);
            ps.setString(1, crewName);
            ps.setString(2, flightNumber);

            return ps.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during flight crew setting!", e);
        } finally {
            closeAll(rs, ps, pool, connection);
        }
    }

    @Override
    public String flightCrew(Flight flight) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = pool.takeConnection();

            ps = connection.prepareStatement(FLIGHT_CREW);
            ps.setDate(1, Date.valueOf(flight.getDepartureDate()));
            ps.setString(2, flight.getFlightNumber());

            rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }
            return rs.getString("short-name");
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during flight crew searching!", e);
        } finally {
            closeAll(rs, ps, pool, connection);
        }
    }

    @Override
    public List<String> takenOnFlightsCrews() throws DaoException {//todo  redo! same with allCrews()
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        List<String> countries = new ArrayList<>();

        try {
            connection = pool.takeConnection();
            st = connection.createStatement();

            rs = st.executeQuery(TAKEN_ON_FLIGHTS_CREWS);
            while (rs.next()) {
                countries.add(rs.getString("short-name"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during taken on flights crews getting!", e);
        } finally {
            closeAll(rs, st, pool, connection);
        }
        return countries;
    }
}
