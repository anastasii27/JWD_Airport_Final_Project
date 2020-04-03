package by.epam.tr.controller;

import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ConnectionPoolListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            pool.poolInitialization();
        } catch (ConnectionPoolException e) {
            //
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            pool.closeAllConnections();
        } catch (ConnectionPoolException e) {
            //
        }
    }

}
