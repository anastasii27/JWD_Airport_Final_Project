package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.command.Command;
import by.epam.tr.service.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignIn implements Command {

    private final static String ANSWER = "There is no such a user";
    private final static String PATH = "/mmm?action=show_user_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        UserService userService = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(true);
        User user;
        String login;
        String password;

        login = request.getParameter(RequestParameterName.LOGIN);
        password = request.getParameter(RequestParameterName.PASSWORD);

        try {

            user = userService.signIn(login, password);

                if(user != null){

                    session.setAttribute(RequestParameterName.USER, user);
                    response.sendRedirect(request.getContextPath() + PATH);
                }else {

                    request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
                    forwardTo(request,response, JSPPageName.RESULT_PAGE);
                }

        } catch (ServiceException | IOException e) {
           errorPage(response);
        }

    }
}
