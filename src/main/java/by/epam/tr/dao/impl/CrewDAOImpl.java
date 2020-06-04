package by.epam.tr.dao.impl;

import by.epam.tr.dao.CrewDAO;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.time.LocalDate;

public class CrewDAOImpl implements CrewDAO {

    private final static String SELECT_CREW_FOR_NEAREST_FLIGHT = "SELECT `short-name` FROM flights\n" +
                                            "JOIN `planes-characteristic` ON (SELECT `planes-characteristic-id` FROM planes WHERE planes.id = `plane-id` ) = `planes-characteristic`.id\n" +
                                            "JOIN `flight-teams` ON  flights.`flight-team-id` = `flight-teams`.id\n" +
                                            "JOIN `flight-teams-m2m-users` ON   `flight-teams-m2m-users`.`flight-team-id` = `flight-teams`.id \n" +
                                            "JOIN airports AS a1 ON  a1.id = flights.`destination-airport-id`\n" +
                                            "JOIN cities AS c1 ON  c1.id = (SELECT `city-id` FROM airports WHERE airports.`name` = a1.`name`)\n" +
                                            "JOIN airports AS a2 ON a2.id = flights.`departure-airport-id`\n" +
                                            "JOIN cities AS c2 ON  c2.id = (SELECT `city-id` FROM airports WHERE airports.`name` = a2.`name`)\n" +
                                            "WHERE `user-id` = (SELECT id FROM users WHERE surname = ? AND email=?) AND `departure-date` BETWEEN  current_date() AND ? LIMIT 1;\n";

    @Override
    public String userCrewForNearestFlight(String surname, String email, LocalDate lastDayOfRange) throws DAOException {

        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Date lastDate = Date.valueOf(lastDayOfRange);
        String crewName;

        try {
            pool = ConnectionPool.getInstance();
            pool.poolInitialization();
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SELECT_CREW_FOR_NEAREST_FLIGHT);

            ps.setString(1, surname);
            ps.setString(2, email);
            ps.setDate(3, lastDate);

            rs = ps.executeQuery();

            if(!rs.next()){
                return null;
            }

            crewName = rs.getString("short-name");

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Exception during user group selecting operation!");
        }finally{

            if(rs !=  null){
                closeResultSet(rs);
            }

            if(ps != null){
                closeStatement(ps);
            }

            if (pool != null) {
                pool.releaseConnection(connection);
            }
        }

        return crewName;
    }

    private void closeStatement(Statement st) {

        try {
            st.close();
        } catch (SQLException e) {
            //log
        }
    }

    private void closeResultSet(ResultSet rs){

        try {
            rs.close();
        } catch (SQLException e) {
            //log
        }
    }
}
