package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.controller.util.ResponseMessageManager;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import by.epam.airport_system.service.UserService;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class ChangeLogin implements Command {
    private final static String USER_PAGE_PATH = "/airport?action=show_user_page";
    private final static String KEY1 = "local.message.login.same";
    private final static String KEY2 = "local.message.login.taken";
    private final static String KEY3 = "local.message.login.changed";
    private final static String KEY4 = "local.message.login.not_changed";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(true);

        String newLogin = request.getParameter(ParameterName.NEW_LOGIN);
        String oldLogin = request.getParameter(ParameterName.OLD_LOGIN);

        try {
            boolean operationResult = false;
            User user = null;

            String language = String.valueOf(session.getAttribute(ParameterName.LOCAL));
            ResponseMessageManager responseManager = new ResponseMessageManager(language);

            if(newLogin.equals(oldLogin)){
                session.setAttribute(ParameterName.RESULT_INFO, responseManager.getValue(KEY1));
            } else if(userService.getUserByLogin(newLogin) != null){
                session.setAttribute(ParameterName.RESULT_INFO, responseManager.getValue(KEY2));
            }else{
                user = userService.getUserByLogin(oldLogin);
                operationResult = userService.changeLogin(newLogin, user);

                session.setAttribute(ParameterName.RESULT_INFO,getResultMessage(operationResult, responseManager));
            }

            if(operationResult && user != null){
                user.setLogin(newLogin);
                session.setAttribute(ParameterName.USER, user);
            }

            session.setAttribute(ParameterName.PREVIOUS_PAGE, request.getContextPath() + USER_PAGE_PATH);
            response.sendRedirect(JSPPageName.RESULT_PAGE);
        } catch (ServiceException  e) {
            log.error("Cannot execute command for password changing", e);
            response.sendRedirect(JSPPageName.ERROR_PAGE);;
        }
    }

    private String getResultMessage(boolean key, ResponseMessageManager responseManager){
        Map<Boolean, String> answers = new HashMap<>();

        answers.put(true, responseManager.getValue(KEY3));
        answers.put(false, responseManager.getValue(KEY4));

        return answers.get(key);
    }
}
