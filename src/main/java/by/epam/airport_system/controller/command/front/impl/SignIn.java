package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.util.ResponseMessageManager;
import by.epam.airport_system.service.*;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Log4j2
public class SignIn implements Command {
    private final static String USER_PATH = "/airport?action=show_user_page";
    private final static String CURRENT_PAGE_PATH = "/airport?action=show_sign_in_page";
    private final static String ANSWER = "local.message.sign_in.1";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(true);

        String login = request.getParameter(ParameterName.LOGIN);
        String password = request.getParameter(ParameterName.PASSWORD);

        try {
            User user = userService.getUserByLogin(login);
            String url;

            if(user != null && BCrypt.checkpw(password, user.getPassword())){
                user.setPassword(null);
                session.setAttribute(ParameterName.USER, user);
                url = request.getContextPath() + USER_PATH;
            }else {
                String language = String.valueOf(session.getAttribute(ParameterName.LOCAL));
                ResponseMessageManager responseManager = new ResponseMessageManager(language);

                session.setAttribute(ParameterName.RESULT_INFO, responseManager.getValue(ANSWER));
                session.setAttribute(ParameterName.PREVIOUS_PAGE, request.getContextPath()+ CURRENT_PAGE_PATH);
                url = JSPPageName.RESULT_PAGE;
            }
            response.sendRedirect(url);
        } catch (ServiceException | IOException e) {
            log.error("Cannot execute command for signing in", e);
            errorPage(response);
        }
    }
}
