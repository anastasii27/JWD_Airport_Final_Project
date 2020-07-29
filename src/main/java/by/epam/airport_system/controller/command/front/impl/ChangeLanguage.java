package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ChangeLanguage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = request.getParameter(ParameterName.URL);
            String local = ParameterName.LOCAL;

            request.getSession(true).setAttribute(local, request.getParameter(local));
            response.sendRedirect(url);
        } catch (IOException e) {
            log.error("Cannot execute command for language change", e);
            errorPage(response);
        }
    }
}
