package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.service.CityService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Log4j2
public class FlightTimetablePage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CityService cityService = ServiceFactory.getInstance().getCityService();

        try {
            List<String> cityWithAirport = cityService.cityWithAirportList();

            request.setAttribute(ParameterName.CITY_WITH_AIRPORT, cityWithAirport);
            forwardTo(request, response, JSPPageName.FLIGHT_TIMETABLE_PAGE);
        } catch (ServiceException e) {
            log.error("Cannot execute command for flight timetable page", e);
            response.sendRedirect(JSPPageName.ERROR_PAGE);
        }
    }
}
