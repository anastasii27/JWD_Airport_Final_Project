package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class FlightInformation implements Command {

    private static final String ANSWER = "No information";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        UserService userService = ServiceFactory.getInstance().getUserService();
        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        Flight flight;
        ArrayList<User> users;
        String groupName;
        String flightNumber;
        String departureDate;

        groupName = request.getParameter(RequestParameterName.GROUP);
        flightNumber = request.getParameter(RequestParameterName.FLIGHT_NUMBER);
        departureDate = request.getParameter(RequestParameterName.DEPARTURE_DATE);

        try {

            users = userService.userByGroup(groupName);
            flight = flightService.flightInfo(flightNumber, departureDate);

            if(users != null){
                request.setAttribute(RequestParameterName.USERS_LIST, users);
            }
            else {
                request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            }

            if (flight != null){
                request.setAttribute(RequestParameterName.FLIGHT, flight);
            }else {
                request.setAttribute(RequestParameterName.RESULT_INFO1, ANSWER);
            }

            request.setAttribute(RequestParameterName.GROUP, groupName);
            request.setAttribute(RequestParameterName.FLIGHT_NUMBER, flightNumber);
            request.setAttribute(RequestParameterName.DEPARTURE_DATE, departureDate);

            forwardTo(request, response, JSPPageName.FLIGHTS_INFO);

        } catch (ServiceException  e) {
            errorPage(response);
        }
    }
}
