package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.util.GsonConverter;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Log4j2
public class FreeCrew implements Command {//todo доделать + validation

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        String flightDepartureDate;
        String flightDepartureTime;
        String flightDepartureAirportName;

        flightDepartureDate = request.getParameter(RequestParameterName.DEPARTURE_DATE);
        flightDepartureTime = request.getParameter(RequestParameterName.DEPARTURE_TIME);
        flightDepartureAirportName = request.getParameter(RequestParameterName.DEPARTURE_AIRPORT);

       String flightDestinationDate = request.getParameter(RequestParameterName.DESTINATION_DATE);
       String flightDestinationTime = request.getParameter(RequestParameterName.DESTINATION_TIME);
       String flightDestinationAirportName = request.getParameter(RequestParameterName.DESTINATION_AIRPORT);

        Flight flight = Flight.builder().departureDate(LocalDate.parse(flightDepartureDate))
                                        .departureTime(LocalTime.parse(flightDepartureTime))
                                        .departureAirportShortName(flightDepartureAirportName)
                                        .destinationDate(LocalDate.parse(flightDestinationDate))
                                        .destinationTime(LocalTime.parse(flightDestinationTime))
                                        .destinationAirportShortName(flightDestinationAirportName)
                                        .build();
        try {
            Set<String> freeCrews = crewService.findFreeCrewsForFlight(flight);
            request.setAttribute(RequestParameterName.CREW, freeCrews);

            String freeCrewsGson = GsonConverter.convertToGson(freeCrews);
            response.getWriter().write(freeCrewsGson);
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for free crews searching", e);
        } catch (IOException e) {
            log.error("Cannot write json to response", e);
        }
    }
}
