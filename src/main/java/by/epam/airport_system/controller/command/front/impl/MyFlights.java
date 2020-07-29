package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.controller.util.RequestToMapParser;
import by.epam.airport_system.controller.util.ResponseMessageManager;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import by.epam.airport_system.service.UserFlightsService;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Log4j2
public class MyFlights implements Command {
    private static final String ANSWER = "local.message.disp_flights.1";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserFlightsService userFlightsService = ServiceFactory.getInstance().getUserFlightsService();

        String departureDate = request.getParameter(ParameterName.DEPARTURE_DATE);
        try {
            Map<String, String> params = RequestToMapParser.toRequestParamsMap(request);
            List<Flight> flights = userFlightsService.userFlights(params);

            if(flights.size()!= 0){
                flights.sort(Flight.SORT_BY_TIME_AND_DATE);
                request.setAttribute(ParameterName.FLIGHT, flights);
            }else {
                String language = String.valueOf(request.getSession().getAttribute(ParameterName.LOCAL));
                ResponseMessageManager resourceManager = new ResponseMessageManager(language);

                request.setAttribute(ParameterName.RESULT_INFO, resourceManager.getValue(ANSWER));
            }
            request.setAttribute(ParameterName.DEPARTURE_DATE, departureDate);

            forwardTo(request,response, JSPPageName.MY_FLIGHTS_PAGE);
        } catch (ServiceException e) {
            log.error("Cannot execute command for user flights", e);
            errorPage(response);
        }
    }
}
