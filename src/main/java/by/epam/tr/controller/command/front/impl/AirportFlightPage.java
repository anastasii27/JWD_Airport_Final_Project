package by.epam.tr.controller.command.front.impl;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.ListCreationService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AirportFlightPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        ListCreationService listCreationService  = ServiceFactory.getInstance().getListCreationService();
        List<String> citiesWithAirports;
        String from;

        from = request.getParameter(RequestParameterName.FROM);

        try {

            citiesWithAirports = listCreationService.createCityWithAirportList();
            request.setAttribute(RequestParameterName.CITY_WITH_AIRPORT, citiesWithAirports);
            request.setAttribute(RequestParameterName.FROM, from);

            forwardTo(request,response, JSPPageName.FLIGHTS_SEARCH_PAGE);

        } catch (ServiceException e) {
            errorPage(response);
        }
    }

}
