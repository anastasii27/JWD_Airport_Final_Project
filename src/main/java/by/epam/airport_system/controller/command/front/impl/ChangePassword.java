package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.RequestParameterName;
import by.epam.airport_system.controller.util.ResponseMessageManager;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import by.epam.airport_system.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class ChangePassword implements Command {
    private final static String USER_PAGE_PATH = "/airport?action=show_user_page";
    private final static String KEY1 = "local.message.password.wrong";
    private final static String KEY2 = "local.message.password.different";
    private final static String KEY3 = "local.message.password.changed";
    private final static String KEY4 = "local.message.password.not_changed";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(true);

        String oldPassword = request.getParameter(RequestParameterName.OLD_PASSWORD);
        String newPassword = request.getParameter(RequestParameterName.NEW_PASSWORD);
        String confirmedPassword = request.getParameter(RequestParameterName.CONFIRMED_PASSWORD);
        String login = request.getParameter(RequestParameterName.LOGIN);

        try {
            User user = userService.getUserByLogin(login);

            String language = String.valueOf(session.getAttribute(RequestParameterName.LOCAL));
            ResponseMessageManager responseManager = new ResponseMessageManager(language);

            if(!BCrypt.checkpw(oldPassword, user.getPassword())){
                session.setAttribute(RequestParameterName.RESULT_INFO, responseManager.getValue(KEY1));
            }else if(!newPassword.equals(confirmedPassword)){
                session.setAttribute(RequestParameterName.RESULT_INFO, responseManager.getValue(KEY2));
            }else {
                boolean operationResult = userService.changePassword(newPassword,user);

                session.setAttribute(RequestParameterName.RESULT_INFO,getResultMessage(operationResult, responseManager));
            }

            session.setAttribute(RequestParameterName.PREVIOUS_PAGE, request.getContextPath() + USER_PAGE_PATH);
            response.sendRedirect(JSPPageName.RESULT_PAGE);
        } catch (ServiceException | IOException e) {
            log.error("Cannot execute command for password changing", e);
            errorPage(response);
        }
    }

    private String getResultMessage(boolean key, ResponseMessageManager responseManager){
        Map<Boolean, String> answers = new HashMap<>();

        answers.put(true,  responseManager.getValue(KEY3));
        answers.put(false, responseManager.getValue(KEY4));

        return answers.get(key);
    }
}
