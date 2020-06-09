package by.epam.tr.dao;

import java.util.List;

public interface ListCreationDAO {

    List<String> createCityWithAirportList() throws DAOException;
    List<String> createRolesList() throws DAOException;
    List<String> createCrewsList() throws DAOException;
}
