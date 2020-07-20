package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.RequestParameterName;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import by.epam.airport_system.service.UserService;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Log4j2
public class CreateCrewPage implements Command {
    private static final String PILOT = "pilot";
    private static final String STEWARD = "steward";
    private final static String CURRENT_PAGE_PATH = "/airport?action=show_create_crew_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(true);
        List<User> pilots;
        List<User> stewards;

        try {
            pilots = userService.userByRoleList(PILOT);
            stewards = userService.userByRoleList(STEWARD);

            request.setAttribute(RequestParameterName.PILOTS, pilots);
            request.setAttribute(RequestParameterName.STEWARDS, stewards);

            session.setAttribute(RequestParameterName.PREVIOUS_PAGE, request.getContextPath()+ CURRENT_PAGE_PATH);

            forwardTo(request, response, JSPPageName.CREATE_CREW_PAGE);
        } catch (ServiceException e) {
            log.error("Cannot execute command for creating crew page", e);
            errorPage(response);
        }
    }
}
