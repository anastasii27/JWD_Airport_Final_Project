package by.epam.airport_system.controller.command.ajax.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.controller.util.GsonConverter;
import by.epam.airport_system.controller.util.RequestParametersExtractor;
import by.epam.airport_system.service.FlightService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Log4j2
public class AirportFlight implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        FlightService flightService = ServiceFactory.getInstance().getFlightService();

        String flightType = request.getParameter(ParameterName.FLIGHT_TYPE);
        String departureDate = request.getParameter(ParameterName.DEPARTURE_DATE);
        String cityWithAirport = request.getParameter(ParameterName.CITY);
        String airportShortName = RequestParametersExtractor.extractAirportName(cityWithAirport);

        try {
            List<Flight> flights = flightService.airportFlightsByDay(flightType, LocalDate.parse(departureDate), airportShortName);

            String flightsGson = GsonConverter.convertToGson(flights);
            response.getWriter().write(flightsGson);
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for arrivals and departure table", e);
        } catch (IOException e) {
            log.error("Cannot write json to response", e);
        }
    }
}

