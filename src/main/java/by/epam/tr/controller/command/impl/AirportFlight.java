package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ListCreationService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

public class AirportFlight implements Command {

    private static final String ANSWER = "No flights";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response){

        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        ListCreationService listCreationService  = ServiceFactory.getInstance().getListCreationService();
        List<Flight> flights;
        List<String> citiesWithAirports;
        String date;
        LocalDate departureDate;
        String city;
        String airportName;
        String flightType;
        String from;

        date = request.getParameter(RequestParameterName.DEPARTURE_DATE);
        departureDate = LocalDate.parse(date);

        city = request.getParameter(RequestParameterName.CITY);
        airportName = getAirportName(city);
        flightType = request.getParameter(RequestParameterName.FLIGHT_TYPE);
        from = request.getParameter(RequestParameterName.FROM);

        try {

            flights = flightService.allFlightsList(departureDate, airportName, flightType);
            citiesWithAirports = listCreationService.createCityWithAirportList();

            if(flights != null){
                request.setAttribute(RequestParameterName.FLIGHT, flights);
                request.setAttribute(RequestParameterName.FLIGHT_TYPE, flightType);
            }else {
                request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            }

            request.setAttribute(RequestParameterName.FROM, from);
            request.setAttribute(RequestParameterName.CITY, city);
            request.setAttribute(RequestParameterName.CITY_WITH_AIRPORT, citiesWithAirports);
            request.setAttribute(RequestParameterName.DEPARTURE_DATE, departureDate);

            forwardTo(request,response, JSPPageName.DEPARTURES_ARRIVALS);

        } catch (ServiceException e) {
            errorPage(response);
        }
    }

    private String getAirportName(String city){

        int indexOfFirstBracket = city.indexOf('(')+1;
        int indexOfLastBracket = city.indexOf(')');

        String airportName = city.substring(indexOfFirstBracket, indexOfLastBracket);

        return airportName;
    }
}

