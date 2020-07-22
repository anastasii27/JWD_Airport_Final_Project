package by.epam.airport_system.dao;

import by.epam.airport_system.dao.connectionpool.ConnectionPool;
import by.epam.airport_system.dao.connectionpool.ConnectionPoolException;
import com.ibatis.common.jdbc.ScriptRunner;
import org.junit.After;
import org.junit.Before;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class H2DataBaseCreation {
    private final static String SCRIPT_PATH = "src\\test\\resources\\h2Script.sql";
    private final static String DROP_TABLE1 = "DROP TABLE flights;";
    private final static String DROP_TABLE2 = "DROP TABLE airports;";
    private final static String DROP_TABLE3 = "DROP TABLE cities;";
    private final static String DROP_TABLE4 = "DROP TABLE countries;";
    private final static String DROP_TABLE5 = "DROP TABLE planes;";
    private final static String DROP_TABLE6 = "DROP TABLE `plane-models`;";
    private final static String DROP_TABLE7 = "DROP TABLE `flight-teams-m2m-users`;";
    private final static String DROP_TABLE8 = "DROP TABLE `flight-teams`;";
    private final static String DROP_TABLE9 = "DROP TABLE users;";
    private final static String DROP_TABLE10 = "DROP TABLE roles;";
    private ConnectionPool pool;
    private Connection connection;

    @Before
    public  void createDataBase() throws ConnectionPoolException, SQLException, IOException {
        pool = ConnectionPool.getInstance();
        pool.poolInitialization();
        connection = pool.takeConnection();

        ScriptRunner sr = new ScriptRunner(connection, false, false);
        try(Reader reader = new BufferedReader(new FileReader(SCRIPT_PATH))) {
            sr.runScript(reader);
        }
    }

    @After
    public void destroyDataBase() throws SQLException, ConnectionPoolException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE1);
            statement.execute(DROP_TABLE2);
            statement.execute(DROP_TABLE3);
            statement.execute(DROP_TABLE4);
            statement.execute(DROP_TABLE5);
            statement.execute(DROP_TABLE6);
            statement.execute(DROP_TABLE7);
            statement.execute(DROP_TABLE8);
            statement.execute(DROP_TABLE9);
            statement.execute(DROP_TABLE10);
        } finally {
            pool.releaseConnection(connection);
            pool.close();
        }
    }
}


