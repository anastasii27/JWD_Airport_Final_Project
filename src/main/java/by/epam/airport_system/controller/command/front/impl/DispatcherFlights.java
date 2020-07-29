package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.RequestParameterName;
import by.epam.airport_system.controller.util.ResponseMessageManager;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import by.epam.airport_system.service.UserFlightsService;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Log4j2
public class DispatcherFlights implements Command {
    private static final String ANSWER = "local.message.disp_flights.1";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserFlightsService userFlightsService = ServiceFactory.getInstance().getUserFlightsService();
        User user  = (User) request.getSession().getAttribute(RequestParameterName.USER);
        String surname = "";
        String email = "";

        if(user != null) {
            surname = user.getSurname();
            email = user.getEmail();
        }

        List<Flight> flights;
        try {
            flights = userFlightsService.dispatcherFlights(surname, email);

            if(flights.size()!= 0){
                flights.sort(Flight.SORT_BY_TIME_AND_DATE);
                request.setAttribute(RequestParameterName.FLIGHT, flights);
            }else {
                String language = String.valueOf(request.getSession().getAttribute(RequestParameterName.LOCAL));
                ResponseMessageManager resourceManager = new ResponseMessageManager(language);

                request.setAttribute(RequestParameterName.RESULT_INFO, resourceManager.getValue(ANSWER));
            }

            forwardTo(request,response, JSPPageName.DISPATCHER_FLIGHTS_PAGE);
        } catch (ServiceException e) {
            log.error("Cannot execute command for dispatcher flights", e);
            errorPage(response);
        }
    }
}