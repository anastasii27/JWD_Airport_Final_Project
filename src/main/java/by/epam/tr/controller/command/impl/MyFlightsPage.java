package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;

public class MyFlightsPage implements Command {

    private static final String ANSWER = "Sorry, no flights were found";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        User user  = (User) request.getSession().getAttribute("user");
        ArrayList<Flight> flight;
        String date;
        LocalDate departureDate;

        date = request.getParameter(RequestParameterName.DEPARTURE_DATE);
        departureDate = LocalDate.parse(date);

        try {

            flight = flightService.userFlightsList(user.getSurname(), user.getEmail(), departureDate);

            if(flight == null){
                request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            }else {
                request.setAttribute(RequestParameterName.FLIGHT, flight);
                request.setAttribute(RequestParameterName.DEPARTURE_DATE, departureDate);
            }

            forwardTo(request,response, JSPPageName.MY_FLIGHTS_PAGE);

        } catch (ServiceException e) {
            errorPage(response);
        }
    }
}
