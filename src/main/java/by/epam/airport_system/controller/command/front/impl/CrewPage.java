package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.service.CrewService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import by.epam.airport_system.service.UserService;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Log4j2
public class CrewPage implements Command {
    private static final String PILOT = "pilot";
    private static final String STEWARD = "steward";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = ServiceFactory.getInstance().getUserService();
        CrewService crewService = ServiceFactory.getInstance().getCrewService();

        try {
            List<String> crews = crewService.allCrews();
            List<User> pilots = userService.userByRoleList(PILOT);
            List<User> stewards = userService.userByRoleList(STEWARD);

            request.setAttribute(ParameterName.CREW, crews);
            request.setAttribute(ParameterName.PILOTS, pilots);
            request.setAttribute(ParameterName.STEWARDS, stewards);

            forwardTo(request, response, JSPPageName.CREWS_PAGE);
        } catch (ServiceException e) {
            log.error("Cannot execute command for crew page", e);
            errorPage(response);
        }
    }
}
