package by.epam.tr.controller.command.front.impl;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.CityService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Log4j2
public class FlightTimetablePage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CityService cityService = ServiceFactory.getInstance().getCityService();

        try {
            List<String> cityWithAirport = cityService.cityWithAirportList();

            request.setAttribute(RequestParameterName.CITY_WITH_AIRPORT, cityWithAirport);
            forwardTo(request, response, JSPPageName.FLIGHT_TIMETABLE_PAGE);
        } catch (ServiceException e) {
            log.error("Cannot execute command for flight timetable page", e);
            errorPage(response);
        }
    }
}
