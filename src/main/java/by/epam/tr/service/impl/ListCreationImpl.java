package by.epam.tr.service.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.dao.ListCreationDAO;
import by.epam.tr.service.ListCreationService;
import by.epam.tr.service.ServiceException;
import java.util.List;

public class ListCreationImpl implements ListCreationService {

    private ListCreationDAO listCreationDAO = DAOFactory.getInstance().getListCreationDAO();

    @Override
    public List<String> createCityWithAirportList() throws ServiceException {

        List <String> citiesWithAirports;

        try {
            citiesWithAirports = listCreationDAO.createCityWithAirportList();

        } catch (DAOException e) {
            throw new ServiceException("Exception during city with airport list creation");
        }
        return citiesWithAirports;
    }

    @Override
    public List<String> createRolesList() throws ServiceException {

        List <String> roles;

        try {
            roles = listCreationDAO.createRolesList();

        } catch (DAOException e) {
            throw new ServiceException("Exception during roles list creation");
        }
        return roles;
    }

    @Override
    public List<String> createCrewsList() throws ServiceException {

        List <String> crews;
        try {
            crews = listCreationDAO.createCrewsList();

        } catch (DAOException e) {
            throw new ServiceException("Exception during crews list creation");
        }
        return crews;
    }

    @Override
    public List<User> createUserByRoleList(String role) throws ServiceException {

        List <User> users;
        try {
            users = listCreationDAO.createUserByRoleList(role);

        } catch (DAOException e) {
            throw new ServiceException("Exception during users by role list creation");
        }
        return users;
    }
}
