package by.epam.tr.dao;

import java.util.List;

public interface CityDao {
    List<String> cityWithAirportList() throws DaoException;
    List<String> cityWithAirportList(String country) throws DaoException;
}
