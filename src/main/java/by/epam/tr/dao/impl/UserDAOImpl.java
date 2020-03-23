package by.epam.tr.dao.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.UserDAO;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import java.sql.*;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    private final static String INSERT = "INSERT INTO task3.users (user_name,login, password) VALUES(?,?,?)";
    private final static String SELECT = "SELECT user_name ,login, password FROM task3.users";
    private final static String SELECT1 = "SELECT user_name FROM task3.users WHERE login = ? AND password = ?";

    @Override
    public boolean addNewUser(User user) throws DAOException {

        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        boolean flag = false;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(INSERT);
            ps.setString(1,user.getName());
            ps.setString(2,user.getLogin());
            ps.setString(3,user.getPassword());
            ps.executeUpdate();
            flag = true;

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during pool initializing!");
        } catch (SQLException e) {
            throw new DAOException("Exception during inserting operation");
        }finally {
            if(ps != null)
                closeStatement(ps);

           pool.releaseConnection(connection);
        }

        return flag;
    }

    @Override
    public ArrayList<String> getUserInfo(String info) throws DAOException {

        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ArrayList <String> users = new ArrayList<>();

            try {
                pool = ConnectionPool.getInstance();
                connection = pool.takeConnection();
                ps =  connection.prepareStatement(SELECT);

                ResultSet rs = ps.executeQuery();

            while (rs.next()){
                users.add(rs.getString(info));
            }

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during pool initializing!");
        } catch (SQLException e) {
            throw new DAOException("Exception during select operation!");
        }finally{
                if(ps != null){
                    closeStatement(ps);
                }

                if (pool != null) {
                    pool.releaseConnection(connection);
                }
            }

        return users;
    }

    @Override
    public boolean singIn(String login, String password) throws DAOException {

        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();
            ps =  connection.prepareStatement(SELECT1);

            ps.setString(1, login);
            ps.setString(2,password);

            ResultSet rs = ps.executeQuery();

            if(!rs.next())
                return false;


        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during pool initializing!");
        } catch (SQLException e) {
            throw new DAOException("Exception during select operation!");
        }finally {
            if(ps != null){
                closeStatement(ps);
            }

            if (pool != null) {
                pool.releaseConnection(connection);
            }
        }

        return true;
    }

    private static void closeStatement(PreparedStatement ps) throws DAOException  {
        try {
            ps.close();
        } catch (SQLException e) {
            throw new DAOException("Exception during statement closing");
        }
    }
}
