package by.epam.tr.service;

import java.util.List;

public interface CountryService {
    List<String> countriesList() throws ServiceException;
}
