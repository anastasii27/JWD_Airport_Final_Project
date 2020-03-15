package by.epam.tr.dao.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private String driver;
    private String user;
    private String password;
    private String url;
    private int poolSize;
    private BlockingQueue<Connection> availableConnection;
    private BlockingQueue<Connection> usedConnection;
    private static ConnectionPool instance;

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
            availableConnection = new ArrayBlockingQueue<Connection>(poolSize);
            usedConnection = new ArrayBlockingQueue<Connection>(poolSize);

            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                availableConnection.add(connection);
            }

        } catch (SQLException e) {
            throw new ConnectionPoolException("SQLException during pool creation!");
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("There is no driver!");
        }
    }


    public Connection takeConnection() throws ConnectionPoolException {

        Connection connection;
        checkForPoolExistence();

        try {
            connection = availableConnection.take();
            usedConnection.add(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Exception during connectionpool taking!");
        }

        return connection;
    }


    public void releaseConnection(Connection connection) throws ConnectionPoolException {

        checkForPoolExistence();

        if(usedConnection.contains(connection)){
            usedConnection.remove(connection);
            availableConnection.add(connection);
        }
    }

    public void closeAllConnections() throws ConnectionPoolException {

        checkForPoolExistence();

        try {
            closeConnectionsInPool(availableConnection);
            closeConnectionsInPool(usedConnection);

        } catch (SQLException e) {
            throw new ConnectionPoolException("Exception during closing operation!");
        }
    }

    private void closeConnectionsInPool(BlockingQueue <Connection> queue) throws SQLException {

        Connection connection;

        while ((connection = queue.poll()) != null) {
            connection.close();
        }
        queue.clear();
    }

    private void checkForPoolExistence() throws ConnectionPoolException {
        PoolExistenceValidation poolExistenceValidation = new PoolExistenceValidation();

        if(!poolExistenceValidation.doesPoolExist(availableConnection) || !poolExistenceValidation.doesPoolExist(usedConnection))
            throw new ConnectionPoolException("Pool haven`t been initialized!!");
    }

}
