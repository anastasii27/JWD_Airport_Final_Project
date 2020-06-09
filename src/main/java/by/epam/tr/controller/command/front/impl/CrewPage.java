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

public class CrewPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        ListCreationService listCreationService  = ServiceFactory.getInstance().getListCreationService();
        List<String> crews;

        try {

            crews = listCreationService.createCrewsList();
            request.setAttribute(RequestParameterName.CREW, crews);
            forwardTo(request, response, JSPPageName.CREWS_PAGE);

        } catch (ServiceException e) {
            errorPage(response);
        }
    }
}
