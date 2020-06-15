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
import java.time.LocalDate;
import java.util.List;

public class MyFlights implements Command {

    private Logger logger = LogManager.getLogger(getClass());
    private static final String ANSWER = "Sorry, no flights were found";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        User user  = (User) request.getSession().getAttribute("user");
        List<Flight> flights;
        String date;
        LocalDate departureDate;
        String surname;
        String email;

        surname = user.getSurname();
        email = user.getEmail();

        date = request.getParameter(RequestParameterName.DEPARTURE_DATE);
        departureDate = LocalDate.parse(date);

        try {
            flights = flightService.userFlights(surname, email, departureDate);

            if(flights.size()!= 0){
                request.setAttribute(RequestParameterName.FLIGHT, flights);
            }else {
                request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            }

            request.setAttribute(RequestParameterName.DEPARTURE_DATE, departureDate);
            forwardTo(request,response, JSPPageName.MY_FLIGHTS_PAGE);

        } catch (ServiceException e) {
            logger.error("Cannot execute command for user flights", e);
            errorPage(response);
        }
    }
}
