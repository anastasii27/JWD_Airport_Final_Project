package by.epam.tr.dao.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.CloseOperation;
import by.epam.tr.dao.CrewDAO;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrewDAOImpl extends CloseOperation implements CrewDAO{

    private final static String SELECT_CREW_MEMBERS = "SELECT `name`, `surname`, title FROM `flight-teams-m2m-users`\n"+
                                            "JOIN users ON `flight-teams-m2m-users`.`user-id` = users.id \n"+
                                            "JOIN roles ON roles.id  = (SELECT `role-id` FROM users WHERE users.id = `flight-teams-m2m-users`.`user-id` )\n"+
                                            "WHERE `flight-teams-m2m-users`.`flight-team-id` = (SELECT id FROM `flight-teams` WHERE `short-name` = ? );";

    private final static String CREATE_CREW = "INSERT INTO `flight-teams`(`date-of-creating`, `short-name`)\n" +
                                            "VALUES (current_date(), ?);";

    private final static String ADD_MEMBER = "INSERT INTO airport.`flight-teams-m2m-users`(`flight-team-id`, `user-id`) VALUES (\n" +
                                            "(SELECT id FROM `flight-teams` WHERE `short-name` =?),\n" +
                                            "(SELECT id FROM users WHERE `name`=? AND surname =?)\n" +
                                            ");";

    private final static String CHECK_CREW_NAME_EXISTENCE = "SELECT `date-of-creating` FROM `flight-teams` WHERE `short-name`=?;";

    private final static String DELETE_CREW_MEMBER = "DELETE FROM airport.`flight-teams-m2m-users` WHERE `user-id` = " +
                                            "(SELECT id FROM users WHERE `name`=? AND surname =?)\n" +
                                            "AND `flight-team-id`= (SELECT id FROM `flight-teams` WHERE `short-name`=?);";

    private final static String DELETE_CREW_WITH_MEMBERS = "DELETE FROM airport.`flight-teams-m2m-users` WHERE `flight-team-id` =" +
                                            " (SELECT id FROM `flight-teams` WHERE `short-name` = ?)";

    private final static String DELETE_CREW= "DELETE FROM airport.`flight-teams` WHERE `short-name` = ?";

    private ConnectionPool pool = ConnectionPool.getInstance();
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean flag;

    @Override
    public List<User> crewMembers(String crewName) throws DAOException {

        List<User> crew = new ArrayList<>();
        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SELECT_CREW_MEMBERS);

            ps.setString(1, crewName);

            rs = ps.executeQuery();

            while (rs.next()) {
                crew.add(new User(rs.getString("name"), rs.getString("surname"), rs.getString("title")));
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during crew members searching!");
        }finally{
            closeAll(rs, ps, pool, connection);
        }
        return crew;
    }

    @Override
    public boolean createCrew(String crewName, Map<String, User> users) throws DAOException {

        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);

            ps =  connection.prepareStatement(CREATE_CREW);
            ps.setString(1, crewName);
            ps.executeUpdate();

            for(Map.Entry<String, User> user : users.entrySet()){
                ps =  connection.prepareStatement(ADD_MEMBER);
                ps.setString(1, crewName);
                ps.setString(2, user.getValue().getName());
                ps.setString(3, user.getValue().getSurname());
                ps.executeUpdate();
            }

            connection.commit();

            flag = true;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during crew creation");
        }finally {
            closeAll(ps, pool, connection);
        }
        return flag;
    }

    @Override
    public boolean doesCrewNameExist(String crewName) throws DAOException {

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(CHECK_CREW_NAME_EXISTENCE);

            ps.setString(1, crewName);

            rs = ps.executeQuery();

            if(!rs.next()){
                return false;
            }

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during crew existence checking!");
        }finally {
            closeAll(rs, ps, pool, connection);
        }
        return true;
    }

    //todo не удаляет, если в бригаде нет участников
    @Override
    public void deleteCrew(String crewName) throws DAOException {

        try {
            pool.poolInitialization();
            connection = pool.takeConnection();
            connection.setAutoCommit(false);

            ps =  connection.prepareStatement(DELETE_CREW_WITH_MEMBERS);
            ps.setString(1, crewName);
            ps.executeUpdate();

            ps =  connection.prepareStatement(DELETE_CREW);
            ps.setString(1, crewName);
            ps.executeUpdate();

            connection.commit();

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Exception during crew deleting");
        }finally {
            closeAll(ps, pool, connection);
        }
    }

    @Override
    public void deleteCrewMember(String crewName, User user) throws DAOException {

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(DELETE_CREW_MEMBER);

            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, crewName);

            ps.executeUpdate();

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during crew member deleting!");
        }finally {
            closeAll(rs, ps, pool, connection);
        }
    }

    public static void main(String[] args) throws DAOException {
        new CrewDAOImpl().deleteCrew("M9");
    }
}
