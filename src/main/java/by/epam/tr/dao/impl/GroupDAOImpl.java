package by.epam.tr.dao.impl;

import by.epam.tr.bean.Group;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.GroupDAO;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.util.ArrayList;

public class GroupDAOImpl implements GroupDAO {

    private final static String SELECT_USER_GROUPS =  "SELECT `short-name`, `date-of-creating` FROM `flight-teams-m2m-users` JOIN users ON users.id =  `flight-teams-m2m-users`.`user-id` " +
                                                      "JOIN `flight-teams` ON `flight-teams-m2m-users`.`flight-team-id` = `flight-teams`.id WHERE users.login = ?;";

    @Override
    public ArrayList<Group> userGroups(String login) throws DAOException {

        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList <Group> groups = new ArrayList<>();

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SELECT_USER_GROUPS);

            ps.setString(1,login);

            rs = ps.executeQuery();

            while (rs.next()){
                groups.add(new Group(rs.getString("short-name"), rs.getDate("date-of-creating").toLocalDate()));
            }


        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
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

        return groups;
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
