package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class FlightsPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response){

        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        ArrayList<Flight> flight;

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSPPageName.FLIGHTS_PAGE);

        try {

            flight = flightService.allFlightsList();
            //null check
            request.setAttribute(RequestParameterName.FLIGHT, flight);

            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }

        } catch (ServletException | IOException | ServiceException e) {
            errorPage(response);
        }
    }
}
