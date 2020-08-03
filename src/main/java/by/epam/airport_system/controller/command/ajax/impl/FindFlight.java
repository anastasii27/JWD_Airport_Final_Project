package by.epam.airport_system.controller.command.ajax.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.controller.util.GsonConverter;
import by.epam.airport_system.service.FlightService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import static by.epam.airport_system.controller.util.RequestParametersExtractor.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Log4j2
public class FindFlight implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        FlightService flightService = ServiceFactory.getInstance().getFlightService();

        String departureCityWithAirport = request.getParameter(ParameterName.DEPARTURE_AIRPORT);
        String destinationCityWithAirport = request.getParameter(ParameterName.DESTINATION_AIRPORT);

        String departureAirport = extractAirportName(departureCityWithAirport);
        String destinationAirport = extractAirportName(destinationCityWithAirport);

        try {
            List<Flight> flights = flightService.findFlights(departureAirport, destinationAirport);
            String flightsGson = GsonConverter.convertToGson(flights);

            response.getWriter().write(flightsGson);
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for flight searching", e);
        }
    }
}
