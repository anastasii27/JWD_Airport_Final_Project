package by.epam.tr.service;

import by.epam.tr.bean.User;
import java.util.List;

public interface ListCreatorService {
    List<String> createCityWithAirportList() throws ServiceException;
    List<String> createCityWithAirportList(String country) throws ServiceException;
    List<String> createRolesList() throws ServiceException;
    List<User> createUserByRoleList(String role) throws ServiceException;
    List<String> createCountriesList() throws ServiceException;
}
