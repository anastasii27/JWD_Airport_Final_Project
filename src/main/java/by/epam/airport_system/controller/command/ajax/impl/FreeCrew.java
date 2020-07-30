package by.epam.airport_system.controller.command.ajax.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import static by.epam.airport_system.controller.util.RequestParametersExtractor.*;
import by.epam.airport_system.controller.util.GsonConverter;
import by.epam.airport_system.service.CrewService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Log4j2
public class FreeCrew implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CrewService crewService = ServiceFactory.getInstance().getCrewService();

        String flightDepartureDate = request.getParameter(ParameterName.DEPARTURE_DATE);
        String flightDepartureTime = request.getParameter(ParameterName.DEPARTURE_TIME);
        String flightDepartureAirportName = request.getParameter(ParameterName.DEPARTURE_AIRPORT);
        String flightDestinationDate = request.getParameter(ParameterName.DESTINATION_DATE);
        String flightDestinationTime = request.getParameter(ParameterName.DESTINATION_TIME);
        String flightDestinationAirportName = request.getParameter(ParameterName.DESTINATION_AIRPORT);

        Flight flight = Flight.builder().departureDate(LocalDate.parse(flightDepartureDate))
                                        .departureTime(LocalTime.parse(flightDepartureTime))
                                        .departureAirportShortName(extractAirportName(flightDepartureAirportName))
                                        .destinationDate(LocalDate.parse(flightDestinationDate))
                                        .destinationTime(LocalTime.parse(flightDestinationTime))
                                        .destinationAirportShortName(extractAirportName(flightDestinationAirportName))
                                        .build();

        try {
            Set<String> freeCrews = crewService.findFreeCrewsForFlight(flight);
            request.setAttribute(ParameterName.CREW, freeCrews);

            String freeCrewsGson = GsonConverter.convertToGson(freeCrews);
            response.getWriter().write(freeCrewsGson);
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for free crews searching", e);
        } catch (IOException e) {
            log.error("Cannot write json to response", e);
        }
    }
}

