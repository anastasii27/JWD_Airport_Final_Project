package by.epam.tr.dao;

import by.epam.tr.bean.User;
import java.util.List;

public interface ListCreatorDAO {
    List<String> createCityWithAirportList() throws DAOException;
    List<String> createCityWithAirportList(String country) throws DAOException;
    List<String> createRolesList() throws DAOException;
    List<String> createCrewsList() throws DAOException;
    List<User> createUserByRoleList(String role) throws DAOException;
    List<String> createCountriesList() throws DAOException;
}
