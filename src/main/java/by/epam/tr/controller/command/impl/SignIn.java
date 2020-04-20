package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.Group;
import by.epam.tr.bean.User;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.PageByRole;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.command.Command;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.UserService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class SignIn implements Command {

    private final static String ANSWER = "There is no such a user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        UserService userService = ServiceFactory.getInstance().getUserService();
        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        PageByRole page = PageByRole.getInstance();
        ArrayList<Group> group;
        ArrayList<Flight> flight;
        User user;

        HttpSession session = request.getSession(true);

        String login = request.getParameter(RequestParameterName.LOGIN);
        String password = request.getParameter(RequestParameterName.PASSWORD);

        try {

            user = userService.singIn(login, password);
            flight = flightService.userFlightsList(login);

                if(user != null){

                    group = userService.userGroups(login);

                    session.setAttribute(RequestParameterName.GROUP, group);
                    session.setAttribute(RequestParameterName.USER_INFO, user);
                    session.setAttribute(RequestParameterName.FLIGHT, flight);

                    response.sendRedirect(page.getPage(user.getRole()));

                }else {

                    request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSPPageName.RESULT_PAGE);

                    if(requestDispatcher!= null){
                        requestDispatcher.forward(request, response);
                    }
                }

        } catch (ServiceException | IOException| ServletException e) {
           errorPage(response);
        }

    }
}
