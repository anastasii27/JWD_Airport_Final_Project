package by.epam.airport_system.service.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.dao.FlightDao;
import by.epam.airport_system.service.FlightService;
import by.epam.airport_system.service.ServiceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlightServiceImpl implements FlightService {
    private FlightDao dao = DaoFactory.getInstance().getFlightDAO();
    private final static String ARRIVAL = "arrival";
    private final static String DEPARTURE = "departure";

    @Override
    public List<Flight> airportFlightsByDay(String flightType, LocalDate departureDate, String airportShortName) throws ServiceException {
        List<Flight> flights = new ArrayList<>();

        try {
            if(flightType.equals(ARRIVAL)){
                flights = dao.airportArrivals(departureDate, airportShortName);
            }
            if(flightType.equals(DEPARTURE)){
               flights = dao.airportDepartures(departureDate, airportShortName);
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception during getting arrivals/departures", e);
        }
        return flights;
    }

    @Override
    public Flight flightInfo(String flightNumber, LocalDate departureDate) throws ServiceException {
        try {
            return dao.flightInfo(flightNumber,  departureDate);
        } catch (DaoException e) {
            throw new ServiceException("Exception during getting flight info", e);
        }
    }

    @Override
    public boolean createFlight(Flight flight) throws ServiceException {
        try {
           return dao.createFlight(flight) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Exception during flight creating", e);
        }
    }

    @Override
    public List<Flight> allFlightByDay(LocalDate departureDate) throws ServiceException {
        try {
            return dao.allFlightByDay(departureDate);
        } catch (DaoException e) {
            throw new ServiceException("Exception during all flights by day getting", e);
        }
    }

    @Override
    public boolean doesFlightNumberExist(String flightNumber, LocalDate date) throws ServiceException {
        try {
            return dao.doesFlightNumberExist(flightNumber, date);
        } catch (DaoException e) {
            throw new ServiceException("Exception during flight existence check", e);
        }
    }

    @Override
    public boolean deleteFlight(String flightNumber, LocalDate departureDate) throws ServiceException {
        try {
            return dao.deleteFlight(flightNumber, departureDate) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Exception during flight deleting", e);
        }
    }

    @Override
    public boolean editFlight(Flight flight) throws ServiceException {
        try {
            return dao.editFlight(flight) != 0;
        } catch (DaoException e) {
            throw new ServiceException("Exception during flight editing", e);
        }
    }

    @Override
    public List<Flight> findFlight(String departureAirport, String destinationAirport) throws ServiceException {
        try {
            return dao.findFlight(departureAirport, destinationAirport, lastDayOfYear());
        } catch (DaoException e) {
            throw new ServiceException("Exception during flight editing", e);
        }
    }

    private LocalDate lastDayOfYear(){
        LocalDate today = LocalDate.now();
        return today.withDayOfYear(today.lengthOfYear());
    }
}
