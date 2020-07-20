package by.epam.airport_system.service;

import java.util.List;

public interface CountryService {
    List<String> countriesList() throws ServiceException;
}
