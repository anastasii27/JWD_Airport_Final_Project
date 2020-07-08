package by.epam.tr.controller.command.front.impl;

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
public class RegisterPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = ServiceFactory.getInstance().getUserService();
        List<String> roles;

        try {
            roles = userService.rolesList();
            request.setAttribute(RequestParameterName.ROLE, roles);

            forwardTo(request,response,JSPPageName.REGISTER_PAGE);
        } catch (ServiceException e) {
            log.error("Cannot execute command for register page", e);
            errorPage(response);
        }
    }
}
