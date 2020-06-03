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
import java.util.ArrayList;

public class FlightServiceImpl implements FlightService {

    @Override
    public ArrayList<Flight> userFlightsList(String surname, String email, LocalDate departureDate) throws ServiceException {

        FlightDAO dao = DAOFactory.getInstance().getFlightDAO();
        ArrayList<Flight> flights;

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
    public ArrayList<Flight> allFlightsList(LocalDate departureDate) throws ServiceException {

        FlightDAO dao = DAOFactory.getInstance().getFlightDAO();
        ArrayList<Flight> flights;

        if(!dateValidation(departureDate)){
            return null;
        }

        try {

            flights =  dao.allFlightsList(departureDate);

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

        FlightDAO dao = DAOFactory.getInstance().getFlightDAO();
        Flight flight;

        try {

            flight =  dao.flightInfo(flightNumber,  departureDate);

        } catch (DAOException e) {
            throw new ServiceException("Exception during getting flight info");
        }

        return flight;
    }

    private boolean dateValidation(LocalDate date){

        Validator dateValidation = ValidationFactory.getInstance().getDateValidation();

        if(!dateValidation.validate(date)){
            return false;
        }

        return true;
    }
}
