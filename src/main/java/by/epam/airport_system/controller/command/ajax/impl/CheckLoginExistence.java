package by.epam.airport_system.controller.command.ajax.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.RequestParameterName;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import by.epam.airport_system.service.UserService;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class CheckLoginExistence implements Command {
    private final static String USER_EXIST = "false";
    private final static String USER_DOES_NOT_EXIST = "true";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = ServiceFactory.getInstance().getUserService();
        String login = request.getParameter(RequestParameterName.LOGIN);

        try {
            User user = userService.getUserByLogin(login);

            if(user == null){
                response.getWriter().write(USER_DOES_NOT_EXIST);
            }else {
                response.getWriter().write(USER_EXIST);
            }
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for login existence check", e);
        } catch (IOException e) {
            log.error("Cannot write response", e);
        }
    }
}
