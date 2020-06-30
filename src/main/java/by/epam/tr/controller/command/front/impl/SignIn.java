package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.command.Command;
import by.epam.tr.service.*;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

@Log4j2
public class SignIn implements Command {
    private final static String ANSWER = "There is no such a user";
    private final static String USER_PATH = "/airport?action=show_user_page";
    private final static String ADMIN_PATH = "/airport?action=show_flight_management_page&departure_date=";
    private final static String ADMIN_ROLE = "admin";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(true);
        String login;
        String password;

        login = request.getParameter(RequestParameterName.LOGIN);
        password = request.getParameter(RequestParameterName.PASSWORD);

        User user;
        try {
            user = userService.signIn(login, password);

            if(user != null){
                session.setAttribute(RequestParameterName.USER, user);
                response.sendRedirect(request.getContextPath() + findPathForRedirect(user.getRole()));
            }else {
                request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
                forwardTo(request,response, JSPPageName.RESULT_PAGE);
            }
        } catch (ServiceException | IOException e) {
            log.error("Cannot execute command for signing in", e);
            errorPage(response);
        }
    }

    private String findPathForRedirect(String userRole){
        if(userRole.equals(ADMIN_ROLE)){
            return ADMIN_PATH + LocalDate.now();
        }else {
            return USER_PATH;
        }
    }
}
