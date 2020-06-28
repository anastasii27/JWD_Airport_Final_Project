package by.epam.tr.controller.command.front.impl;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.ListCreatorService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AirportFlightPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        ListCreatorService listCreatorService = ServiceFactory.getInstance().getListCreatorService();
        List<String> citiesWithAirports;
        String from;

        from = request.getParameter(RequestParameterName.FROM);
        try {
            citiesWithAirports = listCreatorService.createCityWithAirportList();
            request.setAttribute(RequestParameterName.CITY_WITH_AIRPORT, citiesWithAirports);
            request.setAttribute(RequestParameterName.FROM, from);

            forwardTo(request,response, JSPPageName.DEPARTURES_ARRIVALS_PAGE);
        } catch (ServiceException e) {
            log.error("Cannot execute command for flights page", e);
            errorPage(response);
        }
    }
}
