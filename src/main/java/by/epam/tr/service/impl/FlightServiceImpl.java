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
import java.util.List;

public class FlightServiceImpl implements FlightService {

    private FlightDAO dao = DAOFactory.getInstance().getFlightDAO();

    @Override
    public List<Flight> userFlightsList(String surname, String email, LocalDate departureDate) throws ServiceException {

        List<Flight> flights;

        if(!dateValidation(departureDate)){
            return null;
        }

        try {

            flights = dao.userFlightsList(surname, email, departureDate);

            if(flights.size() == 0){
                return null;
            }

        } catch (DAOException e) {
            throw new ServiceException("Exception during getting users flights");
        }

        return flights;
    }

    @Override
    public List<Flight> allFlightsList(LocalDate departureDate, String airportShortName, String type) throws ServiceException {

        Validator flightTypeValidation = ValidationFactory.getInstance().getFlightTypeValidation();
        List<Flight> flights;

        if(!flightTypeValidation.validate(type) && !dateValidation(departureDate)){
            return null;
        }

        try {
            flights =  dao.allFlightsList(departureDate, airportShortName, type);

            if(flights.size() == 0){
                return null;
            }

        } catch (DAOException e) {
            throw new ServiceException("Exception during getting all flights");
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

            if(flights.size() == 0){
                return null;
            }

        } catch (DAOException e) {
            throw new ServiceException("Exception during getting all flights");
        }

        return flights;
    }

    private boolean dateValidation(LocalDate date){

        Validator dateValidation = ValidationFactory.getInstance().getDateValidation();

        if(!dateValidation.validate(date)){
            return false;
        }

        return true;
    }
}
