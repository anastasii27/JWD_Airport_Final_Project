package by.epam.airport_system.dao.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static ConnectionPool instance;
    private final static String FILE_NAME = "db";
    private String driver;
    private String user;
    private String password;
    private String url;
    private int poolSize;
    private BlockingQueue<Connection> availableConnection;
    private BlockingQueue<Connection> usedConnection;

    private ConnectionPool(){
        ResourceBundle bundle = ResourceBundle.getBundle(FILE_NAME);

        driver = bundle.getString(DBConnectionParameter.DRIVER.getKey());
        user = bundle.getString(DBConnectionParameter.USER.getKey());
        password = bundle.getString(DBConnectionParameter.PASSWORD.getKey());
        url = bundle.getString(DBConnectionParameter.URL.getKey());
        poolSize = Integer.parseInt(bundle.getString(DBConnectionParameter.POOL_SIZE.getKey()));
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
            throw new ConnectionPoolException("Exception during pool creation!", e);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Exception! there is no driver!", e);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Exception during putting element into the queue", e);
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;

        try {
            connection = availableConnection.poll();
            usedConnection.put(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Exception during taking connection!", e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection){
        usedConnection.remove(connection);
        availableConnection.add(connection);
    }

    public void close() throws ConnectionPoolException {
        try {
            closeEachConnection(availableConnection);
            closeEachConnection(usedConnection);

        } catch (SQLException e) {
            throw new ConnectionPoolException("Exception during connection closing operation!", e);
        }
    }

    private void closeEachConnection(BlockingQueue <Connection> queue) throws SQLException {
        Connection connection;

        while ((connection = queue.poll()) != null) {
            connection.close();
        }
        queue.clear();
    }
}
