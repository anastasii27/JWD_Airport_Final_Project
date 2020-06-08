package by.epam.tr.service.impl;

import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.dao.ListCreationDAO;
import by.epam.tr.service.ListCreationService;
import by.epam.tr.service.ServiceException;
import java.util.List;

public class ListCreationImpl implements ListCreationService {

    @Override
    public List<String> createCityWithAirportList() throws ServiceException {

        ListCreationDAO listCreationDAO = DAOFactory.getInstance().getListCreationDAO();
        List <String> citiesWithAirports;

        try {

            citiesWithAirports = listCreationDAO.createCityWithAirportList();

        } catch (DAOException e) {
            throw new ServiceException("Exception during city with airport list creation");
        }

        return citiesWithAirports;
    }
}
