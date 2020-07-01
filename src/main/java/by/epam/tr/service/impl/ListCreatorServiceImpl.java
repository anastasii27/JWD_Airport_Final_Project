package by.epam.tr.service.impl;

import by.epam.tr.bean.User;
import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.DaoFactory;
import by.epam.tr.dao.ListCreatorDao;
import by.epam.tr.service.ListCreatorService;
import by.epam.tr.service.ServiceException;
import java.util.List;

public class ListCreatorServiceImpl implements ListCreatorService {
    private ListCreatorDao dao = DaoFactory.getInstance().getListCreatorDAO();

    @Override
    public List<String> createCityWithAirportList() throws ServiceException {
        try {
            return dao.createCityWithAirportList();
        } catch (DaoException e) {
            throw new ServiceException("Exception during city with airport list creation", e);
        }
    }

    @Override
    public List<String> createCityWithAirportList(String country) throws ServiceException {
        try {
            return dao.createCityWithAirportList(country);
        } catch (DaoException e) {
            throw new ServiceException("Exception during city with airport list creation", e);
        }
    }

    @Override
    public List<String> createRolesList() throws ServiceException {
        try {
            return dao.createRolesList();
        } catch (DaoException e) {
            throw new ServiceException("Exception during roles list creation", e);
        }
    }

    @Override
    public List<User> createUserByRoleList(String role) throws ServiceException {
        try {
            return dao.createUserByRoleList(role);
        } catch (DaoException e) {
            throw new ServiceException("Exception during users by role list creation", e);
        }
    }

    @Override
    public List<String> createCountriesList() throws ServiceException {
        try {
            return dao.createCountriesList();
        } catch (DaoException e) {
            throw new ServiceException("Exception during country list creation", e);
        }
    }
}
