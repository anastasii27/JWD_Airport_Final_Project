package by.epam.tr.controller.command.impl;

import by.epam.tr.controller.JSPPageName;
import by.epam.tr.controller.RequestParameterName;
import by.epam.tr.controller.command.Command;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.UserService;
import javax.servlet.http.HttpServletRequest;

public class SingIn implements Command {

    private final static String RESULT = "singed in";

    @Override
    public String execute(HttpServletRequest request) {

        UserService service = ServiceFactory.getInstance().getUserDAO();
        String result;
        String page;

        String login = request.getParameter(RequestParameterName.LOGIN);
        String password = request.getParameter(RequestParameterName.PASSWORD);

        try {

            result = service.singIn(login, password);

            if(result.equals(RESULT))
                return JSPPageName.HOME_PAGE;

            request.setAttribute(RequestParameterName.RESULT_INFO, result);
            page = JSPPageName.RESULT_PAGE;

        } catch (ServiceException e) {
            page = JSPPageName.ERROR_PAGE;
        }
        return page;
    }
}
