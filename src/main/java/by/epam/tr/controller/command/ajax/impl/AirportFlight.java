package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ListCreationService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AirportFlight implements Command {

    private static final String ANSWER = "No flights";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

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

        System.out.println("hello");
        date = request.getParameter(RequestParameterName.DEPARTURE_DATE);
        departureDate = LocalDate.parse(date);

        city = request.getParameter(RequestParameterName.CITY);
        airportName = getAirportName(city);
        flightType = request.getParameter(RequestParameterName.FLIGHT_TYPE);
        from = request.getParameter(RequestParameterName.FROM);

        System.out.println(city+ flightType+ from);
        String flightsGson;
        String citiesWithAirportsGson;

        try {

            flights = flightService.allFlightsList(departureDate, airportName, flightType);
            citiesWithAirports = listCreationService.createCityWithAirportList();

            flightsGson=convertListIntoGSON(flights);
            citiesWithAirportsGson = convertListIntoGSON(citiesWithAirports);

            response.setContentType("application/json");


            System.out.println(flightsGson);
            System.out.println(citiesWithAirportsGson);


            response.getWriter().write(flightsGson);
            response.getWriter().write(citiesWithAirportsGson);

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

    private String convertListIntoGSON(List list){

        Gson gson = new Gson();
        String gsonList = gson.toJson(list);

        return gsonList;
    }
}

