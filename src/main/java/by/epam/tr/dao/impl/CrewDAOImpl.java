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
}
