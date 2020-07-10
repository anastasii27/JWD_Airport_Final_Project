package by.epam.tr.service.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.DaoFactory;
import by.epam.tr.dao.FlightDao;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlightServiceImpl implements FlightService {
    private FlightDao dao = DaoFactory.getInstance().getFlightDAO();
    private final static String ARRIVAL = "arrival";
    private final static String DEPARTURE = "departure";

    @Override
    public List<Flight> airportFlightsByDay(Map<String, String> params) throws ServiceException {
        String flightType = params.get("type");
        List<Flight> flights = new ArrayList<>();

        try {
            if(flightType.equals(ARRIVAL)){
                flights = dao.airportArrivals(params);
            }
            if(flightType.equals(DEPARTURE)){
               flights = dao.airportDepartures(params);
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
            throw new ServiceException("Exception during flight deleting", e);
        }
    }
}
