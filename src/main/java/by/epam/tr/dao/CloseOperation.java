package by.epam.tr.dao;

import by.epam.tr.dao.connectionpool.ConnectionPool;
import lombok.extern.log4j.Log4j2;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Log4j2
public class CloseOperation{//todo change private method with try catch!

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

    private void closeStatement(Statement st) {
        try {
            st.close();
        } catch (SQLException e) {
            log.error("Exception while closing Statement", e);
        }
    }

    private void closeResultSet(ResultSet rs){
        try {
            rs.close();
        } catch (SQLException e) {
            log.error("Exception while closing ResultSet", e);
        }
    }
}
