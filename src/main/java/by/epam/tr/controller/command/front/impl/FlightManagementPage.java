package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.util.RequestToMapParser;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Log4j2
public class FlightManagementPage implements Command {
    private static final String ANSWER = "Sorry, no flights were found";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        FlightService userFlightsService = ServiceFactory.getInstance().getFlightService();
        String departureDate;
        List<Flight> flights;

        departureDate = request.getParameter(RequestParameterName.DEPARTURE_DATE);
        try {
            Map<String, String> params = RequestToMapParser.toRequestParamsMap(request);
            flights = userFlightsService.allFlightByDay(params);

            if(flights.size()!= 0){
                request.setAttribute(RequestParameterName.FLIGHT, flights);
            }else {
                request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            }
            request.setAttribute(RequestParameterName.DEPARTURE_DATE, departureDate);

            forwardTo(request,response, JSPPageName.FLIGHT_MANAGEMENT_PAGE);
        } catch (ServiceException e) {
            log.error("Cannot execute command for admin flights", e);
            errorPage(response);
        }
    }
}
