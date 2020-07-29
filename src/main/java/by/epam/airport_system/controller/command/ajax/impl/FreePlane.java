package by.epam.airport_system.controller.command.ajax.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.Plane;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.controller.util.GsonConverter;
import by.epam.airport_system.controller.util.RequestParametersExtractor;
import by.epam.airport_system.service.PlaneService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Log4j2
public class FreePlane implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        PlaneService planeService = ServiceFactory.getInstance().getPlaneService();

        String departureCityWithAirport = request.getParameter(ParameterName.DEPARTURE_AIRPORT);
        String departureDate = request.getParameter(ParameterName.DEPARTURE_DATE);
        String departureAirport = RequestParametersExtractor.airportName(departureCityWithAirport);
        String destinationCityWithAirport = request.getParameter(ParameterName.DESTINATION_AIRPORT);
        String destinationAirport = RequestParametersExtractor.airportName(destinationCityWithAirport);
        String destinationTime = request.getParameter(ParameterName.DESTINATION_TIME);
        String destinationDate = request.getParameter(ParameterName.DESTINATION_DATE);

        Flight flight = Flight.builder().departureAirportShortName(departureAirport)
                                        .departureDate(LocalDate.parse(departureDate))
                                        .destinationAirportShortName(destinationAirport)
                                        .destinationDate(LocalDate.parse(destinationDate))
                                        .destinationTime(LocalTime.parse(destinationTime)).build();
        try {
            List<Plane> planes = planeService.freePlanesForFlight(flight);

            String planesGson = GsonConverter.convertToGson(planes);
            response.getWriter().write(planesGson);
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for free planes searching", e);
        } catch (IOException e) {
            log.error("Cannot write json to response", e);
        }
    }
}
