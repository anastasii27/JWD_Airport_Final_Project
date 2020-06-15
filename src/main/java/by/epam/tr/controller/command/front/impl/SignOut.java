package by.epam.tr.controller.command.front.impl;

import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignOut implements Command{

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        session.removeAttribute(RequestParameterName.USER);

        try {

            response.sendRedirect(JSPPageName.START_PAGE);

        } catch (IOException e) {
            logger.error("Cannot execute command for signing out", e);
            errorPage(response);
        }
    }
}
