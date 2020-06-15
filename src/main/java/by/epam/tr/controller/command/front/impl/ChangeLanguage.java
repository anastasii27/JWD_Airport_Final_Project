package by.epam.tr.controller.command.front.impl;

import by.epam.tr.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLanguage implements Command {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String url;

        try {
            url = request.getParameter("url");

            request.getSession(true).setAttribute("local", request.getParameter("local"));
            response.sendRedirect(url);

        } catch (IOException e) {
            logger.error("Cannot execute command for language changing", e);
            errorPage(response);
        }
    }
}
