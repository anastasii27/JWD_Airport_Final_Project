package by.epam.airport_system.controller.listener;

import by.epam.airport_system.dao.connectionpool.ConnectionPool;
import by.epam.airport_system.dao.connectionpool.ConnectionPoolException;
import lombok.extern.log4j.Log4j2;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Log4j2
@WebListener
public class ConnectionPoolListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool pool = ConnectionPool.getInstance();

        try {
            pool.poolInitialization();
        } catch (ConnectionPoolException e) {
            log.error("Connection pool cannot be initialized", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool pool = ConnectionPool.getInstance();

        try {
            pool.close();
        } catch (ConnectionPoolException e) {
            log.error("Connection pool cannot be closed", e);
        }
    }
}
