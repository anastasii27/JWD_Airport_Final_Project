package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Log4j2
public class SignOut implements Command{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        session.removeAttribute(ParameterName.USER);
        session.removeAttribute(ParameterName.RESULT_INFO);
        try {
            response.sendRedirect(JSPPageName.START_PAGE);
        } catch (IOException e) {
            log.error("Cannot execute command for signing out", e);
            errorPage(response);
        }
    }
}
