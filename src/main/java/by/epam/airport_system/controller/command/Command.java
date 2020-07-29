package by.epam.airport_system.controller.command;

import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import lombok.extern.log4j.Log4j2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    void execute(HttpServletRequest request, HttpServletResponse response);

    default void errorPage(HttpServletResponse response){
        try {
            response.sendRedirect(JSPPageName.ERROR_PAGE);
        } catch (IOException e) {
           //todo
        }
    }

    default void forwardTo(HttpServletRequest request, HttpServletResponse response, String page){
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);

        try {
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        } catch (ServletException | IOException e) {
            errorPage(response);
        }
    }
}
