package by.epam.airport_system.service.impl;

import by.epam.airport_system.dao.CountryDao;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.service.CountryService;
import by.epam.airport_system.service.ServiceException;
import java.util.List;

public class CountryServiceImpl implements CountryService {
    private CountryDao dao = DaoFactory.getInstance().getCountryDao();

    @Override
    public List<String> countriesList() throws ServiceException {
        try {
            return dao.countriesList();
        } catch (DaoException e) {
            throw new ServiceException("Exception during country list creation", e);
        }
    }
}
