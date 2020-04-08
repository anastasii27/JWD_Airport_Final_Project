package by.epam.tr.controller.command;

import by.epam.tr.controller.constant_parameter.JSPPageName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {

    void execute(HttpServletRequest request, HttpServletResponse response);

    default void errorPage(HttpServletResponse response){

        try {
            response.sendRedirect(JSPPageName.ERROR_PAGE);
        } catch (IOException e) {
            //log
        }
    }
}
