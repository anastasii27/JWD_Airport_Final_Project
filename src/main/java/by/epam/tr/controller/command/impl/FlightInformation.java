package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class FlightInformation implements Command {

    private static final String ANSWER = "No users";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        UserService userService = ServiceFactory.getInstance().getUserService();
        ArrayList<User> users;
        String groupName;

        groupName = request.getParameter(RequestParameterName.GROUP);

        try {

            users = userService.userByGroup(groupName);

            if(users != null){
                request.setAttribute(RequestParameterName.GROUP, users);
            }
            else {
                request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            }

            forwardTo(request, response, JSPPageName.FLIGHTS_INFO);

        } catch (ServiceException  e) {
            errorPage(response);
        }
    }
}
