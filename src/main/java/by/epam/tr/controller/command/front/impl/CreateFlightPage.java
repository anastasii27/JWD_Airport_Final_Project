package by.epam.tr.controller.command.front.impl;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.ListCreatorService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Log4j2
public class CreateFlightPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        ListCreatorService listCreatorService = ServiceFactory.getInstance().getListCreatorService();
        List<String> countries;

        try {
            countries = listCreatorService.createCountriesList();

            request.setAttribute(RequestParameterName.COUNTRIES, countries);
            forwardTo(request,response, JSPPageName.CREATE_FLIGHT_PAGE);
        } catch (ServiceException e) {
           log.error("Cannot execute command for creating flight page", e);
        }
    }
}
