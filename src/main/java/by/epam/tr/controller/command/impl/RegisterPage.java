package by.epam.tr.controller.command.impl;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSPPageName.REGISTER_PAGE);

        try {
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        } catch (ServletException | IOException e) {
            errorPage(response);
        }
    }
}