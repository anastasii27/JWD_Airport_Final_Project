package by.epam.tr.controller;

import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ConnectionPoolListener implements ServletContextListener {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            pool.poolInitialization();
        } catch (ConnectionPoolException e) {
            logger.error("Connection pool cannot be initialized", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            pool.closeAllConnections();
        } catch (ConnectionPoolException e) {
            logger.error("Connection pool cannot be closed", e);
        }
    }
}
