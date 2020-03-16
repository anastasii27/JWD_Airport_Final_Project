package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.JSPPageName;
import by.epam.tr.controller.RequestParameterName;
import by.epam.tr.controller.command.Command;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.UserService;
import javax.servlet.http.HttpServletRequest;

public class Registration implements Command{

    @Override
    public String execute(HttpServletRequest request) {

        UserService service = ServiceFactory.getInstance().getUserDAO();
        String result;
        String page;

        try {
            result = service.registration(new User(request.getParameter(RequestParameterName.LOGIN), request.getParameter(RequestParameterName.PASSWORD)));
            request.setAttribute(RequestParameterName.RESULT_INFO, result);
            page = JSPPageName.RESULT_PAGE;

        } catch (ServiceException e) {
            page = JSPPageName.ERROR_PAGE;
        }
        return page;
    }
}
