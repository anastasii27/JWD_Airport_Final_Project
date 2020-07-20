package by.epam.airport_system.dao.impl;

import by.epam.airport_system.dao.CountryDao;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.connectionpool.ConnectionPool;
import by.epam.airport_system.dao.connectionpool.ConnectionPoolException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CountryDaoImpl implements CountryDao, CloseOperation {
    private static final String COUNTRIES = "SELECT `name` FROM airport.countries;";

    @Override
    public List<String> countriesList() throws DaoException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        List<String> countries = new ArrayList<>();

        try {
            connection = pool.takeConnection();
            st = connection.createStatement();

            rs = st.executeQuery(COUNTRIES);
            while (rs.next()) {
                countries.add(rs.getString("name"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during creating countries list!", e);
        } finally {
            closeAll(rs, st, pool, connection);
        }
        return countries;
    }
}
