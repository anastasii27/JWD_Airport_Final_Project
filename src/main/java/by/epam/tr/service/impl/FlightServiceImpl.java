package by.epam.tr.service.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.dao.FlightDAO;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.validation.ValidationFactory;
import by.epam.tr.service.validation.Validator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FlightServiceImpl implements FlightService {

    private FlightDAO dao = DAOFactory.getInstance().getFlightDAO();
    private Validator dateValidation = ValidationFactory.getInstance().getDateValidation();

    @Override
    public List<Flight> userFlights(Map<String, String> params) throws ServiceException {

        List<Flight> flights;

        if(dateValidation.validate(params).size()!= 0){
            return Collections.emptyList();
        }
        try {
            flights = dao.userFlights(params);

        } catch (DAOException e) {
            throw new ServiceException("Exception during getting users flights");
        }
        return flights;
    }

    @Override
    public List<Flight> flightsByDay(Map<String, String> params) throws ServiceException {

        List<Flight> flights;

        if(dateValidation.validate(params).size()!= 0){
            return Collections.emptyList();
        }
        try {
            flights =  dao.flightsByDay(params);

        } catch (DAOException e) {
            throw new ServiceException("Exception during getting arrivals/departures");
        }
        return flights;
    }

    @Override
    public Flight flightInfo(String flightNumber, String departureDate) throws ServiceException {

        Flight flight;
        try {
            flight =  dao.flightInfo(flightNumber,  departureDate);

        } catch (DAOException e) {
            throw new ServiceException("Exception during getting flight info");
        }
        return flight;
    }

    @Override
    public List<Flight> nearestUserFlights(String surname, String email) throws ServiceException {

        LocalDate lastDayOfRange = LocalDate.now().plusDays(30);
        List<Flight> flights;
        try {
            flights =  dao.nearestUserFlights(surname, email, lastDayOfRange);

        } catch (DAOException e) {
            throw new ServiceException("Exception during getting nearest flight");
        }
        return flights;
    }

    @Override
    public List<Flight> dispatcherFlights(String surname, String email) throws ServiceException {

        List<Flight> flights;
        List<Flight> flightsAfterDeleting;
        try {
            flights = dao.dispatcherFlight(surname, email);

            flightsAfterDeleting = deleteInappropriateDispatcherFlights(flights);

        } catch (DAOException e) {
            throw new ServiceException("Exception during getting dispatcher flights");
        }
        return flightsAfterDeleting;
    }

    private List<Flight> deleteInappropriateDispatcherFlights(List<Flight> flights){

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
