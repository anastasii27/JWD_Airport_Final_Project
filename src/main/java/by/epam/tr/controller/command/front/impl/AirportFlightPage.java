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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AirportFlightPage implements Command {

    private Logger logger = LogManager.getLogger(getClass());

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

            forwardTo(request,response, JSPPageName.DEPARTURES_ARRIVALS_PAGE);

        } catch (ServiceException e) {
            logger.error("Cannot execute command for flights page", e);
            errorPage(response);
        }
    }
}
