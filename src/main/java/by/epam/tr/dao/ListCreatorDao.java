package by.epam.tr.dao;

import by.epam.tr.bean.User;
import java.util.List;

public interface ListCreatorDao {
    List<String> createCityWithAirportList() throws DaoException;
    List<String> createCityWithAirportList(String country) throws DaoException;
    List<String> createRolesList() throws DaoException;
    List<User> createUserByRoleList(String role) throws DaoException;
    List<String> createCountriesList() throws DaoException;
}
