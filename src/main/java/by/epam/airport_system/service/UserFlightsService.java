package by.epam.airport_system.service;

import by.epam.airport_system.bean.Flight;
import java.time.LocalDate;
import java.util.List;

public interface UserFlightsService {
    List<Flight> stewardAndPilotFlights(int id, LocalDate date) throws ServiceException;
    List<Flight> dispatcherFlights(String surname, String email) throws ServiceException;
}
