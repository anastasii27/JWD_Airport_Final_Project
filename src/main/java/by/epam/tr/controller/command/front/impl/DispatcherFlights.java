package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DispatcherFlights implements Command {

    private Logger logger = LogManager.getLogger(getClass());
    private static final String ANSWER = "Sorry, no flights were found";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        User user  = (User) request.getSession().getAttribute(RequestParameterName.USER);
        String surname;
        String email;

        surname = user.getSurname();
        email = user.getEmail();

        List<Flight> flights;
        try {
            flights = flightService.dispatcherFlights(surname, email);

            if(flights.size()!= 0){
                flights.sort(Flight.SORT_BY_TIME_AND_DATE);
                request.setAttribute(RequestParameterName.FLIGHT, flights);
            }else {
                request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            }

            forwardTo(request,response, JSPPageName.DISPATCHER_FLIGHTS_PAGE);
        } catch (ServiceException e) {
            logger.error("Cannot execute command for dispatcher flights", e);
            errorPage(response);
        }
    }
}
