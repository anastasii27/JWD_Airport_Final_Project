package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.RequestParameterName;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.util.ResponseMessageManager;
import by.epam.airport_system.service.*;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

@Log4j2
public class SignIn implements Command {
    private final static String USER_PATH = "/airport?action=show_user_page";
    private final static String ADMIN_PATH = "/airport?action=show_flight_management_page&departure_date=";
    private final static String CURRENT_PAGE_PATH = "/airport?action=show_sign_in_page";
    private final static String ADMIN_ROLE = "admin";
    private final static String ANSWER = "local.message.sign_in.1";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(true);

        String login = request.getParameter(RequestParameterName.LOGIN);
        String password = request.getParameter(RequestParameterName.PASSWORD);

        try {
            User user = userService.getUserByLogin(login);

            if(user != null && BCrypt.checkpw(password, user.getPassword())){
                session.setAttribute(RequestParameterName.USER, user);
                response.sendRedirect(request.getContextPath() + findPathForRedirect(user.getRole()));
            }else {
                String language = String.valueOf(session.getAttribute(RequestParameterName.LOCAL));
                ResponseMessageManager resourceManager = new ResponseMessageManager(language);

                request.setAttribute(RequestParameterName.RESULT_INFO, resourceManager.getValue(ANSWER));
                session.setAttribute(RequestParameterName.PREVIOUS_PAGE, request.getContextPath()+ CURRENT_PAGE_PATH);
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
