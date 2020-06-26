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
        try {
            return listCreatorDAO.createCityWithAirportList();
        } catch (DAOException e) {
            throw new ServiceException("Exception during city with airport list creation", e);
        }
    }

    @Override
    public List<String> createCityWithAirportList(String country) throws ServiceException {
        try {
            return listCreatorDAO.createCityWithAirportList(country);
        } catch (DAOException e) {
            throw new ServiceException("Exception during city with airport list creation", e);
        }
    }

    @Override
    public List<String> createRolesList() throws ServiceException {
        try {
            return listCreatorDAO.createRolesList();
        } catch (DAOException e) {
            throw new ServiceException("Exception during roles list creation", e);
        }
    }

    @Override
    public List<String> createCrewsList() throws ServiceException {
        try {
            return listCreatorDAO.createCrewsList();
        } catch (DAOException e) {
            throw new ServiceException("Exception during crews list creation", e);
        }
    }

    @Override
    public List<User> createUserByRoleList(String role) throws ServiceException {
        try {
            return listCreatorDAO.createUserByRoleList(role);
        } catch (DAOException e) {
            throw new ServiceException("Exception during users by role list creation", e);
        }
    }

    @Override
    public List<String> createCountriesList() throws ServiceException {
        try {
            return listCreatorDAO.createCountriesList();
        } catch (DAOException e) {
            throw new ServiceException("Exception during country list creation", e);
        }
    }
}
