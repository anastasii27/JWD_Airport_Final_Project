package by.epam.tr.service;

import java.util.List;

public interface CityService {
    List<String> cityWithAirportList() throws ServiceException;
    List<String> cityWithAirportList(String country) throws ServiceException;
}
