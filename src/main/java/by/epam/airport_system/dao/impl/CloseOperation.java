package by.epam.airport_system.dao.impl;

import by.epam.airport_system.dao.connectionpool.ConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface CloseOperation{

    default void closeAll(ResultSet rs, Statement st, ConnectionPool pool, Connection connection){
        if(rs !=  null){
            closeResultSet(rs);
        }
        if(st != null){
            closeStatement(st);
        }
        if (pool != null) {
            pool.releaseConnection(connection);
        }
    }

    default void closeAll(Statement st, ConnectionPool pool, Connection connection){
        if(st != null){
            closeStatement(st);
        }
        if (pool != null) {
            pool.releaseConnection(connection);
        }
    }

    default void closeStatement(Statement st) {
        try {
            st.close();
        } catch (SQLException e) {

        }
    }

    default void closeResultSet(ResultSet rs){
        try {
            rs.close();
        } catch (SQLException e) {

        }
    }
}
