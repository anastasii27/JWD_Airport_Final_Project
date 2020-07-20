package by.epam.airport_system.dao.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.dao.CrewMemberDao;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.connectionpool.ConnectionPool;
import by.epam.airport_system.dao.connectionpool.ConnectionPoolException;
import lombok.extern.log4j.Log4j2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CrewMemberDaoImpl implements CrewMemberDao, CloseOperation {
    private final static String CREW_MEMBERS = "SELECT `name`, `surname`, email, title FROM `flight-teams-m2m-users`\n"+
            "JOIN users ON `flight-teams-m2m-users`.`user-id` = users.id \n"+
            "JOIN roles ON roles.id  = (SELECT `role-id` FROM users WHERE users.id = `flight-teams-m2m-users`.`user-id` )\n"+
            "WHERE `flight-teams-m2m-users`.`flight-team-id` = (SELECT id FROM `flight-teams` WHERE `short-name` = ? );";

    private final static String DELETE_CREW_MEMBER = "DELETE FROM airport.`flight-teams-m2m-users` WHERE `user-id` = " +
            "(SELECT id FROM users WHERE `name`=? AND surname =?)\n" +
            "AND `flight-team-id`= (SELECT id FROM `flight-teams` WHERE `short-name`=?);";

    private final static String ADD_MEMBER = "INSERT INTO airport.`flight-teams-m2m-users`(`flight-team-id`, `user-id`) VALUES (\n" +
            "(SELECT id FROM `flight-teams` WHERE `short-name` =?),\n" +
            "(SELECT id FROM users WHERE `name`=? AND surname =?));";

    private final static String CHECK_CREW_MEMBER_EXISTENCE = "SELECT * FROM airport.`flight-teams-m2m-users` WHERE `user-id` = " +
            "(SELECT id FROM users WHERE `name` = ? AND surname = ?) AND `flight-team-id` = (SELECT id " +
            "FROM `flight-teams` WHERE `short-name` = ?);";
    private int changedRowsAmount;

    @Override
    public List<User> crewMembers(String crewName) throws DaoException {
        List<User> crew = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection  connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);

            ps =  connection.prepareStatement(CREW_MEMBERS);
            ps.setString(1, crewName);
            rs = ps.executeQuery();

            while (rs.next()) {
                crew.add(User.builder().name(rs.getString("name"))
                        .surname(rs.getString("surname"))
                        .role(rs.getString("title"))
                        .email(rs.getString("email")).build());
            }

            connection.commit();
        } catch (ConnectionPoolException | SQLException e) {
            try {
                if(connection != null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                log.error("Exception while executing rollback()", ex);
            }
            throw new DaoException("Exception during crew members searching!", e);
        }finally{
            try {
                if(connection!=null){
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                log.error("Exception while setting auto committing = true", e);
            }
            closeAll(rs, ps, pool, connection);
        }
        return crew;
    }

    @Override
    public int deleteCrewMember(String crewName, User user) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection  connection = null;
        PreparedStatement ps = null;

        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);

            ps =  connection.prepareStatement(DELETE_CREW_MEMBER);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, crewName);
            changedRowsAmount = ps.executeUpdate();

            connection.commit();
        } catch (ConnectionPoolException | SQLException e) {
            try {
                if(connection != null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                log.error("Exception while executing rollback()", ex);
            }
            throw new DaoException("Exception during crew member deleting!", e);
        }finally {
            try {
                if(connection!=null){
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                log.error("Exception while setting auto committing = true", e);
            }
            closeAll( ps, pool, connection);
        }
        return changedRowsAmount;
    }

    @Override
    public int addCrewMember(String crewName, List<User> crewMembers) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection  connection = null;
        PreparedStatement ps = null;

        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);

            ps =  connection.prepareStatement(ADD_MEMBER);
            for (User user: crewMembers) {
                ps.setString(1, crewName);
                ps.setString(2, user.getName());
                ps.setString(3, user.getSurname());
                changedRowsAmount = ps.executeUpdate();
            }

            connection.commit();
        } catch (ConnectionPoolException | SQLException e) {
            try {
                if(connection != null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                log.error("Exception while executing rollback()", ex);
            }
            throw new DaoException("Exception during crew member deleting!", e);
        }finally {
            try {
                if(connection!=null){
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                log.error("Exception while setting auto committing = true", e);
            }
            closeAll(ps, pool, connection);
        }
        return changedRowsAmount;
    }

    @Override
    public boolean isUserInTheCrew(String crewName, User user) throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        ResultSet rs;
        PreparedStatement  ps = null;
        boolean operationResult;

        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);

            ps =  connection.prepareStatement(CHECK_CREW_MEMBER_EXISTENCE);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, crewName);
            rs = ps.executeQuery();
            operationResult  = rs.next();

            connection.commit();
        } catch (ConnectionPoolException | SQLException e) {
            try {
                if(connection != null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                log.error("Exception while executing rollback()", ex);
            }
            throw new DaoException("Exception during user existence operation!", e);
        }finally {
            try {
                if(connection!=null){
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                log.error("Exception while setting auto committing = true", e);
            }
            closeAll(ps, pool, connection);
        }
        return operationResult;
    }
}
