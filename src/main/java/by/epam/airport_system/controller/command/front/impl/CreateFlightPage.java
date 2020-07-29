package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.service.CountryService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Log4j2
public class CreateFlightPage implements Command {
    private final static String CURRENT_PAGE_PATH = "/airport?action=show_create_flight_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CountryService countryService = ServiceFactory.getInstance().getCountryService();
        HttpSession session = request.getSession(true);

        try {
            List<String> countries = countryService.countriesList();

            request.setAttribute(ParameterName.COUNTRIES, countries);
            session.setAttribute(ParameterName.PREVIOUS_PAGE, request.getContextPath()+ CURRENT_PAGE_PATH);

            forwardTo(request,response, JSPPageName.CREATE_FLIGHT_PAGE);
        } catch (ServiceException e) {
           log.error("Cannot execute command for creating flight page", e);
        }
    }
}
