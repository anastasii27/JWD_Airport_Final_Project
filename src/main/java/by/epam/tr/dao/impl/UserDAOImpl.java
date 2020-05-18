package by.epam.tr.dao.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.UserDAO;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    private final static String INSERT =  "INSERT INTO airport.users (`role-id`, login, `password`, `name`, surname, email, `career-start-year`) VALUES((SELECT id FROM airport.roles WHERE title = ?),?,?,?,?,?,?);";
    //private final static String SELECT_USER_INFO =  "SELECT title AS `user-role` ,login ,`name`, surname, email, `career-start-year` FROM airport.users JOIN airport.roles ON roles.id = users.`role-id`;";
    private final static String SELECT_FOR_SING_IN = "SELECT title  AS `role` , `name`, surname, email, `career-start-year` FROM users JOIN roles ON users.`role-id` = roles.id WHERE login = ? AND `password`=?;  ";
    private final static String CHECK_USER_EXISTENCE = "SELECT `name` FROM users WHERE login = ?";
    private final static String SELECT_USER_BY_GROUP = "SELECT `name`, `surname`, title FROM `flight-teams-m2m-users`\n"+
                                                        "JOIN users ON `flight-teams-m2m-users`.`user-id` = users.id \n"+
                                                        "JOIN roles ON roles.id  = (SELECT `role-id` FROM users WHERE users.id = `flight-teams-m2m-users`.`user-id` )\n"+
                                                        "WHERE `flight-teams-m2m-users`.`flight-team-id` = (SELECT id FROM `flight-teams` WHERE `short-name` = ? );";

    @Override
    public boolean addNewUser(User user, String login, String password) throws DAOException {

        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        boolean flag = false;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(INSERT);

            ps.setString(1,user.getRole());
            ps.setString(2,login);
            ps.setString(3,password);
            ps.setString(4,user.getName());
            ps.setString(5,user.getSurname());
            ps.setString(6,user.getEmail());
            ps.setString(7, user.getCareerStartYear());

            ps.executeUpdate();
            flag = true;

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during inserting operation");
        }finally {

            if(ps != null){
                closeStatement(ps);
            }

            if (pool != null) {
                pool.releaseConnection(connection);
            }
        }

        return flag;
    }

    @Override
    public User singIn(String login, String password) throws DAOException {

        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SELECT_FOR_SING_IN);

            ps.setString(1, login);
            ps.setString(2,password);

            rs = ps.executeQuery();

            if(!rs.next()){
                return null;
            }

            user =  new User(rs.getString("role"),rs.getString("name"), rs.getString("surname"), rs.getString("email"),rs.getString("career-start-year"));


        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during sign in operation!");
        }finally {

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

        return user;
    }

//    @Override
//    public ArrayList<User> allUsersInfo() throws DAOException {
//
//        ConnectionPool pool = null;
//        Connection connection = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        ArrayList <User> users = new ArrayList<>();
//
//        try {
//
//            pool = ConnectionPool.getInstance();
//            connection = pool.takeConnection();
//            ps =  connection.prepareStatement(SELECT_USER_INFO);
//
//            rs = ps.executeQuery();
//
//            while (rs.next()){
//                users.add(new User(rs.getString("user-role"),rs.getString("name"),rs.getString("surname"),
//                        rs.getString("email"), rs.getString("career-start-year")));
//            }
//
//        } catch (ConnectionPoolException e) {
//            throw new DAOException("Exception during taking connection!");
//        } catch (SQLException e) {
//            throw new DAOException("Exception during user info selecting operation!");
//        }finally{
//
//            if(rs !=  null){
//                closeResultSet(rs);
//            }
//
//            if(ps != null){
//                closeStatement(ps);
//            }
//
//            if (pool != null) {
//                pool.releaseConnection(connection);
//            }
//        }
//
//        return users;
//    }

    @Override
    public boolean doesUserExist(String login) throws DAOException {

        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(CHECK_USER_EXISTENCE);

            ps.setString(1, login);

            rs = ps.executeQuery();

            if(!rs.next()){
                return false;
            }


        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during user existence operation!");
        }finally {

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

        return true;
    }

    @Override
    public ArrayList<User> userByGroup(String groupName) throws DAOException {

        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList <User> users = new ArrayList<>();

        try {

            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SELECT_USER_BY_GROUP);

            ps.setString(1, groupName);

            rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getString("name"), rs.getString("surname"), rs.getString("title")));
            }

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
        } catch (SQLException e) {
            throw new DAOException("Exception during finding user by group operation!");
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
        return users;
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

