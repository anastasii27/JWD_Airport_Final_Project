package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.util.GsonConverter;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import static by.epam.tr.controller.util.RequestParametersExtractor.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Log4j2
public class FindFlight implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        String departureCityWithAirport = request.getParameter(RequestParameterName.DEPARTURE_AIRPORT);
        String destinationCityWithAirport = request.getParameter(RequestParameterName.DESTINATION_AIRPORT);

        try {
            String departureAirport = airportName(departureCityWithAirport);
            String destinationAirport = airportName(destinationCityWithAirport);

            List<Flight> flights = flightService.findFlight(departureAirport, destinationAirport);
            String flightsGson = GsonConverter.convertToGson(flights);

            response.getWriter().write(flightsGson);
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for flight searching", e);
        } catch (IOException e) {
            log.error("Cannot write json to response", e);
        }
    }
}
