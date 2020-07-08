package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.UserService;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Log4j2
public class CreateCrewPage implements Command {
    private static final String PILOT = "pilot";
    private static final String STEWARD = "steward";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = ServiceFactory.getInstance().getUserService();
        List<User> pilots;
        List<User> stewards;

        try {
            pilots = userService.userByRoleList(PILOT);
            stewards = userService.userByRoleList(STEWARD);

            request.setAttribute(RequestParameterName.PILOTS, pilots);
            request.setAttribute(RequestParameterName.STEWARDS, stewards);

            forwardTo(request, response, JSPPageName.CREATE_CREW_PAGE);
        } catch (ServiceException e) {
            log.error("Cannot execute command for creating crew page", e);
            errorPage(response);
        }
    }
}
