package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.PageByRole;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserPage implements Command {

    private static final String ANSWER = "No flight for next 30 days";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        User user = (User)request.getSession().getAttribute(RequestParameterName.USER);
        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        PageByRole page = PageByRole.getInstance();
        List<Flight> nearestFlights;
        String surname;
        String email;

        surname = user.getSurname();
        email = user.getEmail();

        try {
            nearestFlights = flightService.nearestUserFlights(surname, email);

            if(nearestFlights.size() != 0) {
                request.setAttribute(RequestParameterName.FLIGHT, nearestFlights);
            }
            else{
                request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            }

            forwardTo(request,response,page.getPage(user.getRole()));

        } catch (ServiceException e) {
            errorPage(response);
        }
    }
}
