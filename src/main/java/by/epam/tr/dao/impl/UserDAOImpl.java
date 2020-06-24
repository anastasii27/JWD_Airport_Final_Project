package by.epam.tr.dao.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.CloseOperation;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.UserDAO;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends CloseOperation implements UserDAO {

    private final static String INSERT_USER =  "INSERT INTO airport.users (`role-id`, login, `password`, `name`, surname, email, `career-start-year`)" +
                                    "VALUES((SELECT id FROM airport.roles WHERE title = ?),?,?,?,?,?,?);";

    private final static String SELECT_FOR_SING_IN = "SELECT title  AS `role` , `name`, surname, email, `career-start-year` " +
                                    "FROM users JOIN roles ON users.`role-id` = roles.id WHERE login = ? AND `password`=?;";

    private final static String CHECK_USER_EXISTENCE_1 = "SELECT `name` FROM users WHERE login = ?;";

    private final static String CHECK_USER_EXISTENCE_2 = "SELECT email FROM users where `name`= ? AND surname = ?;";

    private final static String SELECT_ALL_USERS = "SELECT `name`, surname, title FROM airport.users " +
                                    "JOIN roles ON roles.id = users.`role-id`;";

    @Override
    public boolean addNewUser(User user, String login, String password) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        boolean flag = false;
        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(INSERT_USER);

            ps.setString(1,user.getRole());
            ps.setString(2,login);
            ps.setString(3,password);
            ps.setString(4,user.getName());
            ps.setString(5,user.getSurname());
            ps.setString(6,user.getEmail());
            ps.setString(7, user.getCareerStartYear());
            ps.executeUpdate();

            flag = true;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during user registration", e);
        }finally {
            closeAll(ps, pool, connection);
        }
        return flag;
    }

    @Override
    public User signIn(String login, String password) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user;
        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SELECT_FOR_SING_IN);

            ps.setString(1, login);
            ps.setString(2,password);

            rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            }
            user =  new User(rs.getString("role"),rs.getString("name"), rs.getString("surname"), rs.getString("email"),rs.getString("career-start-year"));
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during signing in!", e);
        }finally {
            closeAll(rs, ps, pool, connection);
        }
        return user;
    }

    @Override
    public boolean doesUserExist(String login) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(CHECK_USER_EXISTENCE_1);

            ps.setString(1, login);
            rs = ps.executeQuery();

            return rs.next();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during user existence operation!", e);
        }finally {
            closeAll(rs, ps, pool, connection);
        }
    }

    @Override
    public boolean doesUserExist(User user) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(CHECK_USER_EXISTENCE_2);

            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            rs = ps.executeQuery();

            return rs.next();
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during user existence operation!", e);
        }finally {
            closeAll(rs, ps, pool, connection);
        }
    }

    @Override
    public List<User> allUsers() throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SELECT_ALL_USERS);

            rs = ps.executeQuery();
            while (rs.next()){
                users.add(new User(rs.getString("name"), rs.getString("surname"), rs.getString("title")));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DAOException("Exception during all users getting!", e);
        }finally {
            closeAll(rs, ps, pool, connection);
        }
        return users;
    }
}