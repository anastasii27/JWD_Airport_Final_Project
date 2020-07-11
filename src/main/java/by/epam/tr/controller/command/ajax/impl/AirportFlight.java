package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.util.GsonConverter;
import by.epam.tr.controller.util.RequestParametersExtractor;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
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

        String flightType = request.getParameter(RequestParameterName.FLIGHT_TYPE);
        String departureDate = request.getParameter(RequestParameterName.DEPARTURE_DATE);
        String cityWithAirport = request.getParameter(RequestParameterName.CITY);
        String airportShortName = RequestParametersExtractor.airportName(cityWithAirport);

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

