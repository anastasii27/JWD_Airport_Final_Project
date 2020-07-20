package by.epam.airport_system.service.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.dao.UserFlightsDao;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.UserFlightsService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserFlightsServiceImpl implements UserFlightsService {
    private UserFlightsDao dao = DaoFactory.getInstance().getUserFlightsDao();

    @Override
    public List<Flight> userFlights(Map<String, String> params) throws ServiceException {
        try {
            return dao.userFlights(params);
        } catch (DaoException e) {
            throw new ServiceException("Exception during getting users flights", e);
        }
    }

    @Override
    public List<Flight> nearestUserFlights(String surname, String email) throws ServiceException {
        LocalDate lastDayOfRange = LocalDate.now().plusDays(30);

        try {
            return dao.nearestUserFlights(surname, email, lastDayOfRange);
        } catch (DaoException e) {
            throw new ServiceException("Exception during getting nearest flight", e);
        }
    }

    @Override
    public List<Flight> dispatcherFlights(String surname, String email) throws ServiceException {
        List<Flight> flights;

        try {
            flights = dao.dispatcherFlights(surname, email);

            return deleteInappropriateDispatcherFlights(flights);
        } catch (DaoException e) {
            throw new ServiceException("Exception during getting dispatcher flights", e);
        }
    }

    private List<Flight> deleteInappropriateDispatcherFlights(List<Flight> flights){//todo refactor
        List <Flight> deleteFromFlights = new ArrayList<>();
        LocalDateTime dateTime;

        for(Flight flight: flights){
            dateTime = LocalDateTime.of(flight.getDepartureDate(), flight.getDepartureTime());

            if(dateTime.isBefore(LocalDateTime.now())){
                deleteFromFlights.add(flight);
            }
            if (dateTime.isAfter(LocalDateTime.now().plusHours(12))){
                deleteFromFlights.add(flight);
            }
        }

        flights.removeAll(deleteFromFlights);
        return  flights;
    }
}
