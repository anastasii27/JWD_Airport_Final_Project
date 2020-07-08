package by.epam.tr.dao;

import java.util.List;

public interface CountryDao {
    List<String> countriesList() throws DaoException;
}
