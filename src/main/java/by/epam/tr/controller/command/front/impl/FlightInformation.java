package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
public class FlightInformation implements Command {
    private static final String ANSWER = "No information";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        Flight flight;
        String flightNumber;
        String departureDate;

        flightNumber = request.getParameter(RequestParameterName.FLIGHT_NUMBER);
        departureDate = request.getParameter(RequestParameterName.DEPARTURE_DATE);
        try {
            flight = flightService.flightInfo(flightNumber, departureDate);

            if (flight != null){
                request.setAttribute(RequestParameterName.FLIGHT, flight);
            }else {
                request.setAttribute(RequestParameterName.RESULT_INFO1, ANSWER);
            }

            request.setAttribute(RequestParameterName.FLIGHT_NUMBER, flightNumber);
            request.setAttribute(RequestParameterName.DEPARTURE_DATE, departureDate);

            forwardTo(request, response, JSPPageName.FLIGHTS_INFO_PAGE);
        } catch (ServiceException  e) {
            log.error("Cannot execute command for flights information", e);
            errorPage(response);
        }
    }
}
