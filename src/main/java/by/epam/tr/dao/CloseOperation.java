package by.epam.tr.dao;

import by.epam.tr.dao.connectionpool.ConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CloseOperation{

    public void closeStatement(Statement st) {

        try {
            st.close();
        } catch (SQLException e) {
            //log
        }
    }

    public void closeResultSet(ResultSet rs){

        try {
            rs.close();
        } catch (SQLException e) {
            //log
        }
    }

    public void closeAll(ResultSet rs, Statement st, ConnectionPool pool, Connection connection){

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

    public void closeAll(Statement st, ConnectionPool pool, Connection connection){

        if(st != null){
            closeStatement(st);
        }

        if (pool != null) {
            pool.releaseConnection(connection);
        }
    }
}
