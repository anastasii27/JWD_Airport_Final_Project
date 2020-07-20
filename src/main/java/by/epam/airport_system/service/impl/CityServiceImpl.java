package by.epam.airport_system.service.impl;

import by.epam.airport_system.dao.CityDao;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.service.CityService;
import by.epam.airport_system.service.ServiceException;
import java.util.List;

public class CityServiceImpl implements CityService {
    private CityDao dao  = DaoFactory.getInstance().getCityDao();

    @Override
    public List<String> cityWithAirportList() throws ServiceException {
        try {
            return dao.cityWithAirportList();
        } catch (DaoException e) {
            throw new ServiceException("Exception during city with airport list creation", e);
        }
    }

    @Override
    public List<String> cityWithAirportList(String country) throws ServiceException {
        try {
            return dao.cityWithAirportList(country);
        } catch (DaoException e) {
            throw new ServiceException("Exception during city with airport list creation", e);
        }
    }
}
