package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.RequestParameterName;
import by.epam.airport_system.service.CityService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AirportFlightPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CityService cityService = ServiceFactory.getInstance().getCityService();
        List<String> citiesWithAirports;
        String from;

        from = request.getParameter(RequestParameterName.FROM);
        try {
            citiesWithAirports = cityService.cityWithAirportList();
            request.setAttribute(RequestParameterName.CITY_WITH_AIRPORT, citiesWithAirports);
            request.setAttribute(RequestParameterName.FROM, from);

            forwardTo(request,response, JSPPageName.DEPARTURES_ARRIVALS_PAGE);
        } catch (ServiceException e) {
            log.error("Cannot execute command for flights page", e);
            errorPage(response);
        }
    }
}
