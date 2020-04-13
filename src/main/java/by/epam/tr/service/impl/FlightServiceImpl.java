package by.epam.tr.service.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.dao.DAOException;
import by.epam.tr.dao.DAOFactory;
import by.epam.tr.dao.FlightDAO;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;

import java.util.ArrayList;

public class FlightServiceImpl implements FlightService {

    @Override
    public ArrayList<Flight> userFlightsList(String login) throws ServiceException {

        FlightDAO dao = DAOFactory.getInstance().getFlightDAO();
        ArrayList<Flight> flights;

        try {

            flights =    dao.userFlightsList(login);

        } catch (DAOException e) {
            throw new ServiceException("Exception during getting users flight");
        }

        return flights;
    }

    @Override
    public ArrayList<Flight> allFlightsList() {
        return null;
    }

}
