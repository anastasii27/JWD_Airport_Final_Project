package by.epam.tr.controller.command.impl;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.FlightService;
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
        List<by.epam.tr.bean.Flight> flights;
        String date;
        String airportName;
        String city;
        String flightType;
        LocalDate departureDate;

        city = request.getParameter(RequestParameterName.CITY);
        airportName = getAirportName(city);
        flightType = request.getParameter(RequestParameterName.FLIGHT_TYPE);
        date = request.getParameter(RequestParameterName.DEPARTURE_DATE);

        departureDate = LocalDate.parse(date);

        try {

            flights = flightService.allFlightsList(departureDate, airportName, flightType);

            if(flights != null){
                request.setAttribute(RequestParameterName.FLIGHT, flights);
            }else {
                request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            }

            request.setAttribute(RequestParameterName.DEPARTURE_DATE, departureDate);
            forwardTo(request,response, JSPPageName.DEPARTURES_ARRIVALS);

        } catch (ServiceException e) {
            errorPage(response);
        }
    }

    private String getAirportName(String city){
        return null;
    }

}
