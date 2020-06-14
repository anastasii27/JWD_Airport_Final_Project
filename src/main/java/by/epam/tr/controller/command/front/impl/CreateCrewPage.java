package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.ListCreationService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateCrewPage implements Command {

    private static final String PILOT = "pilot";
    private static final String STEWARD = "steward";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        ListCreationService listCreationService  = ServiceFactory.getInstance().getListCreationService();
        List<User> pilots;
        List<User> stewards;

        try {
            pilots = listCreationService.createUserByRoleList(PILOT);
            stewards = listCreationService.createUserByRoleList(STEWARD);

            request.setAttribute(RequestParameterName.PILOTS, pilots);
            request.setAttribute(RequestParameterName.STEWARDS, stewards);

            forwardTo(request, response, JSPPageName.CREATE_CREW_PAGE);

        } catch (ServiceException e) {
            errorPage(response);
        }
    }
}
