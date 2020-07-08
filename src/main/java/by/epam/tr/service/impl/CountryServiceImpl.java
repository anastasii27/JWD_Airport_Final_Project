package by.epam.tr.service.impl;

import by.epam.tr.dao.CountryDao;
import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.DaoFactory;
import by.epam.tr.service.CountryService;
import by.epam.tr.service.ServiceException;
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
