package by.epam.airport_system.controller.command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

    default void forwardTo(HttpServletRequest request, HttpServletResponse response, String page) throws IOException,
                                                                                                            ServletException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);

        if (requestDispatcher != null) {
            requestDispatcher.forward(request, response);
        }
    }
}
