package by.epam.tr.dao;

import by.epam.tr.bean.User;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;

import javax.jws.soap.SOAPBinding;
import java.sql.*;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    private final static String INSERT =  "INSERT INTO airport.users (role_id, login, user_password, user_name, surname, email, career_start_year) VALUES((SELECT id FROM airport.roles WHERE title = ?),?,?,?,?,?,?);";
    private final static String SELECT =  "SELECT title as user_role ,login ,user_name, surname, email, career_start_year FROM airport.users JOIN airport.roles ON roles.id = users.role_id;";
    private final static String SELECT1 = "SELECT user_name FROM airport.users WHERE login = ? AND user_password = ?";
   // private final static String SELECT2 = "SELECT departure_city, arrival_city, flight_name, departure_time  FROM task3.flight;";

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
            ps.setString(1,user.getRole());
            ps.setString(2,user.getLogin());
            ps.setString(3,user.getPassword());
            ps.setString(4,user.getName());
            ps.setString(5,user.getSurname());
            ps.setString(6,user.getEmail());
            ps.setInt(7,user.getCareer_start_year());
            ps.executeUpdate();
            flag = true;

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
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
    public ArrayList<User> getUsersInfo() throws DAOException {

        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ArrayList <User> users = new ArrayList<>();

            try {
                pool = ConnectionPool.getInstance();
                connection = pool.takeConnection();
                ps =  connection.prepareStatement(SELECT);

                ResultSet rs = ps.executeQuery();

            while (rs.next()){
                users.add(new User(rs.getString("user_role"), rs.getString("login"), null, rs.getString("user_name"),rs.getString("surname"),
                        rs.getString("email"), rs.getInt("career_start_year")));
            }

        } catch (ConnectionPoolException e) {
            throw new DAOException("Exception during taking connection!");
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
            throw new DAOException("Exception during taking connection!");
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

//    @Override
//    public ArrayList <Flight> getFlightInfo() throws DAOException {
//
//        ConnectionPool pool = null;
//        Connection connection = null;
//        PreparedStatement ps = null;
//        ArrayList <Flight> flight = new ArrayList<>();
//
//        try {
//            pool = ConnectionPool.getInstance();
//            pool.poolInitialization();
//            connection = pool.takeConnection();
//            ps =  connection.prepareStatement(SELECT2);
//
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()){
//                flight.add(new Flight(rs.getString("departure_city"), rs.getString("arrival_city"), rs.getString("flight_name"), rs.getString("departure_time")));
//            }
//
//        } catch (ConnectionPoolException e) {
//            throw new DAOException("Exception during taking connection!");
//        } catch (SQLException e) {
//            throw new DAOException("Exception during select operation!");
//        }finally{
//            if(ps != null){
//                closeStatement(ps);
//            }
//            if (pool != null) {
//                pool.releaseConnection(connection);
//            }
//        }
//
//        return flight;
//    }

    private static void closeStatement(PreparedStatement ps) throws DAOException  {
        try {
            ps.close();
        } catch (SQLException e) {
            throw new DAOException("Exception during statement closing");
        }
    }

}

