package by.epam.airport_system.dao.impl;

import by.epam.airport_system.dao.CityDao;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.dao.H2DataBaseCreation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CityDaoImplTest extends H2DataBaseCreation{
    private final static String COUNTRY = "Belarus";
    private final static String CITY_WITH_AIRPORT1 = "Minsk(MSQ1)";
    private final static String CITY_WITH_AIRPORT2 = "Minsk(MSQ2)";
    private final static String ILLEGAL_COUNTRY = "Something";
    private List<String> expected;

    @Before
    public void setUp(){
        expected = new ArrayList<String>(){{
            add(CITY_WITH_AIRPORT1);
            add(CITY_WITH_AIRPORT2);
        }};
    }

    @Test
    public void cityWithAirportList() throws DaoException {
        CityDao cityDao = DaoFactory.getInstance().getCityDao();
        List<String> actual = cityDao.cityWithAirportList();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void cityWithAirportList_whenCountryIsNotNull_thenList() throws DaoException {
        CityDao cityDao = DaoFactory.getInstance().getCityDao();
        List<String> actual = cityDao.cityWithAirportList(COUNTRY);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void cityWithAirportList_whenCountryIsNull_thenEmptyList() throws DaoException {
        CityDao cityDao = DaoFactory.getInstance().getCityDao();
        List<String> actual = cityDao.cityWithAirportList(null);

        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void cityWithAirportList_whenCountryDoesNotExist_thenEmptyList() throws DaoException {
        CityDao cityDao = DaoFactory.getInstance().getCityDao();
        List<String> actual = cityDao.cityWithAirportList(ILLEGAL_COUNTRY);

        Assert.assertEquals(Collections.emptyList(), actual);
    }
}