package by.epam.airport_system.dao.impl;

import by.epam.airport_system.dao.CountryDao;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.dao.H2DataBaseCreation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class CountryDaoImplTest extends H2DataBaseCreation {
    private final static String COUNTRY = "Belarus";
    private List<String> expected;

    @Before
    public void setUp(){
        expected = new ArrayList<>();
        expected.add(COUNTRY);
    }
    @Test
    public void countriesList() throws DaoException {
        CountryDao countryDao = DaoFactory.getInstance().getCountryDao();
        List<String> actual = countryDao.countriesList();

        Assert.assertEquals(expected, actual);
    }
}