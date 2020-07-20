package by.epam.airport_system.dao;

import java.util.List;

public interface CountryDao {
    List<String> countriesList() throws DaoException;
}
