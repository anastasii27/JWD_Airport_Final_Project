package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import by.epam.airport_system.service.UserService;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Log4j2
public class RegisterPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = ServiceFactory.getInstance().getUserService();

        try {
            List<String> roles = userService.rolesList();
            request.setAttribute(ParameterName.ROLE, roles);

            forwardTo(request,response,JSPPageName.REGISTER_PAGE);
        } catch (ServiceException e) {
            log.error("Cannot execute command for register page", e);
            errorPage(response);
        }
    }
}
