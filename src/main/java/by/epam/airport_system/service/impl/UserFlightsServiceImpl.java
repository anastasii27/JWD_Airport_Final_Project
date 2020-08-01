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

public class UserFlightsServiceImpl implements UserFlightsService {
    private UserFlightsDao dao = DaoFactory.getInstance().getUserFlightsDao();

    @Override
    public List<Flight> stewardAndPilotFlights(int id, LocalDate date) throws ServiceException {
        try {
            return dao.userFlights(id, date);
        } catch (DaoException e) {
            throw new ServiceException("Exception during getting pilots/stewards flights", e);
        }
    }

    @Override
    public List<Flight> dispatcherFlights(String surname, String email) throws ServiceException {
        try {
            List<Flight> flights = dao.dispatcherFlights(surname, email);

            return deleteInappropriateDispatcherFlights(flights);
        } catch (DaoException e) {
            throw new ServiceException("Exception during getting dispatcher flights", e);
        }
    }

    private List<Flight> deleteInappropriateDispatcherFlights(List<Flight> flights){
        List <Flight> deleteFromFlights = new ArrayList<>();
        LocalDateTime dateTime;

        for(Flight flight: flights){
            dateTime = LocalDateTime.of(flight.getDepartureDate(), flight.getDepartureTime());

           if(!doesThisFlightApproach(dateTime)){
               deleteFromFlights.add(flight);
           }
        }

        flights.removeAll(deleteFromFlights);
        return  flights;
    }

    private boolean doesThisFlightApproach(LocalDateTime dateTime){
        return !dateTime.isBefore(LocalDateTime.now())
                    &&  !dateTime.isAfter(LocalDateTime.now().plusHours(12));
    }
}
