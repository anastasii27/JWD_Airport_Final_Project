package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.Group;
import by.epam.tr.bean.User;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.command.Command;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class SignIn implements Command {

    private final static String ANSWER = "There is no such a user";
    private final static String PATH = "/mmm?action=show_user_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        UserService userService = ServiceFactory.getInstance().getUserService();
        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        ArrayList<Group> group;
        ArrayList<Flight> flight;
        User user;
        String login;
        String password;

        HttpSession session = request.getSession(true);

        login = request.getParameter(RequestParameterName.LOGIN);
        password = request.getParameter(RequestParameterName.PASSWORD);

        try {

            user = userService.singIn(login, password);

                if(user != null){

                    flight = flightService.userFlightsList(login);
                    group = userService.userGroups(login);

                    session.setAttribute(RequestParameterName.GROUP, group);
                    session.setAttribute(RequestParameterName.USER_INFO, user);
                    session.setAttribute(RequestParameterName.FLIGHT, flight);

                    response.sendRedirect(request.getContextPath() + PATH);

                }else {
                    //delete result page
                    request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
                    forwardTo(request,response, JSPPageName.RESULT_PAGE);
                }

        } catch (ServiceException | IOException e) {
           errorPage(response);
        }

    }
}
