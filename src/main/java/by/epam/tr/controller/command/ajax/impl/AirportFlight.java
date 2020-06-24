package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.util.GSONConverter;
import by.epam.tr.controller.util.RequestToMapParser;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AirportFlight implements Command {

    private Logger LOGGER = LogManager.getLogger(getClass());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        Map<String, String> params;
        List<Flight> flights;
        String flightsGson;
        try {
            params = RequestToMapParser.toFlightValidationParamsMap(request);

            flights = flightService.flightsByDay(params);
            flightsGson = GSONConverter.convertListToGSON(flights);

            response.getWriter().write(flightsGson);
        } catch (ServiceException e) {
            LOGGER.error("Cannot execute ajax command for arrivals and departure table", e);
        } catch (IOException e) {
            LOGGER.error("Cannot write json to response", e);
        }
    }
}

