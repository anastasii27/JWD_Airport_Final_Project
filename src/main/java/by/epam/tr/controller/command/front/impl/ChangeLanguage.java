package by.epam.tr.controller.command.front.impl;

import by.epam.tr.controller.command.Command;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ChangeLanguage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String url;

        try {
            url = request.getParameter("url");

            request.getSession(true).setAttribute("local", request.getParameter("local"));
            response.sendRedirect(url);
        } catch (IOException e) {
            log.error("Cannot execute command for language change", e);
            errorPage(response);
        }
    }
}
