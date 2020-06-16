package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.command.ajax.GSONConverter;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirportFlight implements Command {

    private Logger logger = LogManager.getLogger(getClass());
    private Map<String, String> params =  new HashMap<>();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        String departureDate;
        String city;
        String airportName;
        String flightType;

        departureDate = request.getParameter(RequestParameterName.DEPARTURE_DATE);
        city = request.getParameter(RequestParameterName.CITY);
        airportName = getAirportName(city);
        flightType = request.getParameter(RequestParameterName.FLIGHT_TYPE);

        List<Flight> flights;
        String flightsGson;
        try {

            params = createMap(departureDate, airportName, flightType);

            flights = flightService.flightsByDay(params);
            flightsGson = GSONConverter.convertListToGSON(flights);

            response.getWriter().write(flightsGson);
        } catch (ServiceException e) {
            logger.error("Cannot execute ajax command for arrivals and departure table", e);
        } catch (IOException e) {
            logger.error("Cannot write json to response", e);
        }
    }

    private String getAirportName(String city){

        int indexOfFirstBracket = city.indexOf('(')+1;
        int indexOfLastBracket = city.indexOf(')');

        String airportName = city.substring(indexOfFirstBracket, indexOfLastBracket);

        return airportName;
    }

    private Map<String, String> createMap(String departureDate, String airportName, String flightType){

        params.put("departureDate", departureDate);
        params.put("airportName", airportName);
        params.put("flightType", flightType);

        return params;
    }
}

