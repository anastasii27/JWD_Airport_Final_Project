package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AirportFlight implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        List<Flight> flights;
        String date;
        LocalDate departureDate;
        String city;
        String airportName;
        String flightType;

        date = request.getParameter(RequestParameterName.DEPARTURE_DATE);
        departureDate = LocalDate.parse(date);
        city = request.getParameter(RequestParameterName.CITY);
        airportName = getAirportName(city);
        flightType = request.getParameter(RequestParameterName.FLIGHT_TYPE);

        String flightsGson;

        try {

            flights = flightService.allFlightsList(departureDate, airportName, flightType);
            flightsGson=convertListToGSON(flights);

            response.getWriter().write(flightsGson);

        } catch (ServiceException | IOException e) {
            //
        }

    }

    private String getAirportName(String city){

        int indexOfFirstBracket = city.indexOf('(')+1;
        int indexOfLastBracket = city.indexOf(')');

        String airportName = city.substring(indexOfFirstBracket, indexOfLastBracket);

        return airportName;
    }

    private String convertListToGSON(List list){

        Gson gson = new Gson();
        String gsonList = gson.toJson(list);

        return gsonList;
    }
}

