package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.UserService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class FlightInformation implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        UserService userService = ServiceFactory.getInstance().getUserService();
        ArrayList<User> users;
        String groupName;
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSPPageName.FLIGHTS_INFO);

        groupName = request.getParameter(RequestParameterName.GROUP);

        try {
            users = userService.userByGroup(groupName);

            request.setAttribute(RequestParameterName.GROUP, users);

            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }

        } catch (ServletException | IOException | ServiceException e) {
            errorPage(response);
        }
    }
}
