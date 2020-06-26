package by.epam.tr.dao;

import by.epam.tr.dao.connectionpool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CloseOperation{//todo change private method with try catch!
    private Logger LOGGER = LogManager.getLogger(getClass());

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
            LOGGER.error("Exception while closing Statement", e);
        }
    }

    private void closeResultSet(ResultSet rs){
        try {
            rs.close();
        } catch (SQLException e) {
            LOGGER.error("Exception while closing ResultSet", e);
        }
    }
}
