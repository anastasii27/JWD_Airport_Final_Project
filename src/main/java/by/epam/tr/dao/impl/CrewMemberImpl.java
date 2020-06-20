package by.epam.tr.dao.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.CloseOperation;
import by.epam.tr.dao.CrewMemberDAO;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrewMemberImpl extends CloseOperation implements CrewMemberDAO {

    private final static String SELECT_CREW_MEMBERS = "SELECT `name`, `surname`, title FROM `flight-teams-m2m-users`\n"+
                                            "JOIN users ON `flight-teams-m2m-users`.`user-id` = users.id \n"+
                                            "JOIN roles ON roles.id  = (SELECT `role-id` FROM users WHERE users.id = `flight-teams-m2m-users`.`user-id` )\n"+
                                            "WHERE `flight-teams-m2m-users`.`flight-team-id` = (SELECT id FROM `flight-teams` WHERE `short-name` = ? );";

    private final static String DELETE_CREW_MEMBER = "DELETE FROM airport.`flight-teams-m2m-users` WHERE `user-id` = " +
                                            "(SELECT id FROM users WHERE `name`=? AND surname =?)\n" +
                                            "AND `flight-team-id`= (SELECT id FROM `flight-teams` WHERE `short-name`=?);";

    private final static String ADD_MEMBER = "INSERT INTO airport.`flight-teams-m2m-users`(`flight-team-id`, `user-id`) VALUES (\n" +
                                            "(SELECT id FROM `flight-teams` WHERE `short-name` =?),\n" +
                                            "(SELECT id FROM users WHERE `name`=? AND surname =?));";


    private ConnectionPool pool = ConnectionPool.getInstance();
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    private int changedRowsAmount;

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
    public int deleteCrewMember(String crewName, User user) throws DAOException {

        try {
            pool.poolInitialization();
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(DELETE_CREW_MEMBER);

            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, crewName);

            changedRowsAmount = ps.executeUpdate();

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during crew member deleting!");
        }finally {
            closeAll(rs, ps, pool, connection);
        }
        return changedRowsAmount;
    }

    //todo проверка на существование юзера и команды
    @Override
    public int addCrewMember(String crewName, User user) throws DAOException {

        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(ADD_MEMBER);

            ps.setString(1, crewName);
            ps.setString(2, user.getName());
            ps.setString(3, user.getSurname());

            changedRowsAmount = ps.executeUpdate();

            System.out.println(changedRowsAmount);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Exception during crew member deleting!");
        }finally {
            closeAll(rs, ps, pool, connection);
        }
        return changedRowsAmount;
    }
}
