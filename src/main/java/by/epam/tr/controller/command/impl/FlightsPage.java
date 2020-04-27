package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class FlightsPage implements Command {

    private static final String ANSWER = "No flights";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response){

        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        ArrayList<Flight> flight;

        try {

            flight = flightService.allFlightsList();

            if(flight != null){
                request.setAttribute(RequestParameterName.FLIGHT, flight);
            }else {
                request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            }

            forwardTo(request,response, JSPPageName.FLIGHTS_PAGE);

        } catch (ServiceException e) {
            errorPage(response);
        }
    }
}
