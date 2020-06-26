package by.epam.tr.dao.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static ConnectionPool instance;
    private String driver;
    private String user;
    private String password;
    private String url;
    private int poolSize;
    private BlockingQueue<Connection> availableConnection;
    private BlockingQueue<Connection> usedConnection;

    private ConnectionPool() {
        ResourceManager resourceManager = ResourceManager.getInstance();
        this.driver = resourceManager.getValue(DBConnectionParameter.DRIVER.getKey());
        this.user = resourceManager.getValue(DBConnectionParameter.USER.getKey());
        this.password = resourceManager.getValue(DBConnectionParameter.PASSWORD.getKey());
        this.url = resourceManager.getValue(DBConnectionParameter.URL.getKey());
        this.poolSize = Integer.parseInt(resourceManager.getValue(DBConnectionParameter.POOL_SIZE.getKey()));
    }

    public static ConnectionPool getInstance() {
        if(instance == null){
            instance = new ConnectionPool();
        }
        return instance;
    }

    public void poolInitialization() throws ConnectionPoolException {
        try {
            Class.forName(driver);
            availableConnection = new ArrayBlockingQueue<>(poolSize);
            usedConnection = new ArrayBlockingQueue<>(poolSize);

            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                availableConnection.put(connection);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("Exception during pool creation!");
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Exception! there is no driver!");
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Exception during putting element into the queue");
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;

        try {
            connection = availableConnection.poll();
            usedConnection.put(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Exception during taking connection!");
        }
        return connection;
    }

    public void releaseConnection(Connection connection){
        usedConnection.remove(connection);
        availableConnection.add(connection);
    }

    public void closeAllConnections() throws ConnectionPoolException {
        try {
            closeConnectionsInPool(availableConnection);
            closeConnectionsInPool(usedConnection);

        } catch (SQLException e) {
            throw new ConnectionPoolException("Exception during connection closing operation!");
        }
    }

    private void closeConnectionsInPool(BlockingQueue <Connection> queue) throws SQLException {
        Connection connection;

        while ((connection = queue.poll()) != null) {
            connection.close();
        }
        queue.clear();
    }
}
