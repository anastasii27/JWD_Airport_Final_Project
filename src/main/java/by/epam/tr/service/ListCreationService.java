package by.epam.tr.service;

import java.util.List;

public interface ListCreationService {

    List<String> createCityWithAirportList() throws ServiceException;
    List<String> createRolesList() throws ServiceException;

}
