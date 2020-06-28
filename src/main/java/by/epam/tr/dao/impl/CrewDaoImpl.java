package by.epam.tr.dao.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.CloseOperation;
import by.epam.tr.dao.CrewDao;
import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import lombok.extern.log4j.Log4j2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Log4j2
public class CrewDaoImpl extends CloseOperation implements CrewDao {
    private final static String CREATE_CREW = "INSERT INTO `flight-teams`(`date-of-creating`, `short-name`)\n" +
            "VALUES (current_date(), ?);";

    private final static String ADD_MEMBER = "INSERT INTO airport.`flight-teams-m2m-users`(`flight-team-id`, `user-id`) VALUES (\n" +
            "(SELECT id FROM `flight-teams` WHERE `short-name` =?),\n" +
            "(SELECT id FROM users WHERE `name`=? AND surname =?));";

    private final static String SET_MAIN_PILOT = "UPDATE airport.`flight-teams` \n" +
            "SET `main-pilot-id` = (SELECT id FROM users WHERE `name` = ? AND `surname` = ?)\n" +
            "WHERE `short-name` = ?; ";

    private final static String CHECK_CREW_NAME_EXISTENCE = "SELECT `date-of-creating` FROM `flight-teams` WHERE `short-name`=?;";

    private final static String DELETE_CREW_WITH_MEMBERS = "DELETE FROM airport.`flight-teams-m2m-users` WHERE `flight-team-id` =" +
            "(SELECT id FROM `flight-teams` WHERE `short-name` = ?)";

    private final static String DELETE_CREW = "DELETE FROM airport.`flight-teams` WHERE `short-name` = ?";

    private final static String DELETE_CREW_FROM_FLIGHT = "UPDATE airport.flights\n" +
            "SET `flight-team-id` =  null WHERE `flight-team-id` = (SELECT id FROM `flight-teams` " +
            "WHERE `short-name`= ?);";

    private final static String FIND_MAIN_PILOT = "SELECT `name`, surname FROM airport.`flight-teams`\n" +
            "JOIN users ON `main-pilot-id`  = users.id\n" +
            "WHERE `short-name` = ?;";

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
        try( PreparedStatement deleteWithMembers = connection.prepareStatement(DELETE_CREW_WITH_MEMBERS)){
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
            throw new DaoException("Exception during crew existence checking!", e);
        } finally {
            closeAll(rs, ps, pool, connection);
        }
        return user;
    }
}
