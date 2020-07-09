package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import static by.epam.tr.controller.util.RequestParametersExtractor.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;

@Log4j2
public class EditFlight implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        FlightService flightService = ServiceFactory.getInstance().getFlightService();

        String flightId = request.getParameter(RequestParameterName.ID);
        String flightNumber = request.getParameter(RequestParameterName.FLIGHT_NUMBER);
        String plane = request.getParameter(RequestParameterName.PLANE);
        String crew = request.getParameter(RequestParameterName.CREWS);
        String status = request.getParameter(RequestParameterName.STATUS);
        String departureDate = request.getParameter(RequestParameterName.DEPARTURE_DATE);
        String destinationDate = request.getParameter(RequestParameterName.DESTINATION_DATE);
        String departureTime = request.getParameter(RequestParameterName.DEPARTURE_TIME);
        String destinationTime = request.getParameter(RequestParameterName.DESTINATION_TIME);
        String departureAirport = request.getParameter(RequestParameterName.DEPARTURE_AIRPORT);
        String destinationAirport = request.getParameter(RequestParameterName.DESTINATION_AIRPORT);

        Flight flight = Flight.builder().id(Integer.parseInt(flightId))
                                        .planeNumber(planeNumber(plane))
                                        .flightNumber(flightNumber)
                                        .crew(crew)
                                        .status(status)
                                        .departureDate(LocalDate.parse(departureDate))
                                        .departureTime(LocalTime.parse(departureTime))
                                        .destinationDate(LocalDate.parse(destinationDate))
                                        .destinationTime(LocalTime.parse(destinationTime))
                                        .departureAirportShortName(airportName(departureAirport))
                                        .destinationAirportShortName(airportName(destinationAirport)).build();

        try {
            boolean operationResult = flightService.editFlight(flight);

            if(operationResult){
                request.setAttribute(RequestParameterName.DEPARTURE_DATE, LocalDate.now());
                forwardTo(request, response, JSPPageName.FLIGHT_MANAGEMENT_PAGE);
            }else {
                //todo mess
            }
        } catch (ServiceException e) {
            log.error("Cannot execute command for flight editing", e);
            errorPage(response);
        }

    }
}
