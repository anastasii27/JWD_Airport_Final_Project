package by.epam.tr.service;

public interface CrewService {

    String userCrewForNearestFlight(String surname, String email) throws ServiceException;
}
