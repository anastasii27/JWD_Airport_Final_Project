package by.epam.airport_system.dao.impl;

import by.epam.airport_system.dao.CountryDao;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.connectionpool.ConnectionPool;
import by.epam.airport_system.dao.connectionpool.ConnectionPoolException;
import static by.epam.airport_system.dao.impl.DbParameterName.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CountryDaoImpl implements CountryDao{
    private static final String SELECT_COUNTRIES = "SELECT `name` FROM airport.countries;";

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

            rs = st.executeQuery(SELECT_COUNTRIES);
            while (rs.next()) {
                countries.add(rs.getString(NAME));
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("Exception during creating countries list!", e);
        } finally {
            if(pool != null) {
               pool.closeConnection(rs, st, connection);
            }
        }
        return countries;
    }
}
