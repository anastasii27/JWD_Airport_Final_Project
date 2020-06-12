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

public class CrewDAOImpl extends CloseOperation implements CrewDAO{

//    private final static String SELECT_CREW_FOR_NEAREST_FLIGHT = "SELECT `short-name` FROM flights\n" +
//                                            "JOIN `planes-characteristic` ON (SELECT `planes-characteristic-id` FROM planes WHERE planes.id = `plane-id` ) = `planes-characteristic`.id\n" +
//                                            "JOIN `flight-teams` ON  flights.`flight-team-id` = `flight-teams`.id\n" +
//                                            "JOIN `flight-teams-m2m-users` ON   `flight-teams-m2m-users`.`flight-team-id` = `flight-teams`.id \n" +
//                                            "JOIN airports AS a1 ON  a1.id = flights.`destination-airport-id`\n" +
//                                            "JOIN cities AS c1 ON  c1.id = (SELECT `city-id` FROM airports WHERE airports.`name` = a1.`name`)\n" +
//                                            "JOIN airports AS a2 ON a2.id = flights.`departure-airport-id`\n" +
//                                            "JOIN cities AS c2 ON  c2.id = (SELECT `city-id` FROM airports WHERE airports.`name` = a2.`name`)\n" +
//                                            "WHERE `user-id` = (SELECT id FROM users WHERE surname = ? AND email=?) AND `departure-date` BETWEEN  current_date() AND ? LIMIT 1;\n";


    private  final static String SELECT_CREW_MEMBERS = "SELECT `name`, `surname`, title FROM `flight-teams-m2m-users`\n"+
                                            "JOIN users ON `flight-teams-m2m-users`.`user-id` = users.id \n"+
                                            "JOIN roles ON roles.id  = (SELECT `role-id` FROM users WHERE users.id = `flight-teams-m2m-users`.`user-id` )\n"+
                                            "WHERE `flight-teams-m2m-users`.`flight-team-id` = (SELECT id FROM `flight-teams` WHERE `short-name` = ? );";

    private ConnectionPool pool = ConnectionPool.getInstance();
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;

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
}
