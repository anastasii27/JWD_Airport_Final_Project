package by.epam.tr.service.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.dao.ListCreatorDAO;
import by.epam.tr.service.ListCreatorService;
import by.epam.tr.service.ServiceException;
import java.util.List;

public class ListCreatorImpl implements ListCreatorService {

    private ListCreatorDAO listCreatorDAO = DAOFactory.getInstance().getListCreatorDAO();

    @Override
    public List<String> createCityWithAirportList() throws ServiceException {

        List <String> citiesWithAirports;

        try {
            citiesWithAirports = listCreatorDAO.createCityWithAirportList();

        } catch (DAOException e) {
            throw new ServiceException("Exception during city with airport list creation");
        }
        return citiesWithAirports;
    }

    @Override
    public List<String> createRolesList() throws ServiceException {

        List <String> roles;

        try {
            roles = listCreatorDAO.createRolesList();

        } catch (DAOException e) {
            throw new ServiceException("Exception during roles list creation");
        }
        return roles;
    }

    @Override
    public List<String> createCrewsList() throws ServiceException {

        List <String> crews;
        try {
            crews = listCreatorDAO.createCrewsList();

        } catch (DAOException e) {
            throw new ServiceException("Exception during crews list creation");
        }
        return crews;
    }

    @Override
    public List<User> createUserByRoleList(String role) throws ServiceException {

        List <User> users;
        try {
            users = listCreatorDAO.createUserByRoleList(role);

        } catch (DAOException e) {
            throw new ServiceException("Exception during users by role list creation");
        }
        return users;
    }
}
