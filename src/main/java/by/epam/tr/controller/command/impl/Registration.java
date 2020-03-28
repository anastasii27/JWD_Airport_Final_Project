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

        String role;
        String login;
        String password;
        String name;
        String surname;
        String email;
        int career_start_year;

        role = request.getParameter(RequestParameterName.ROLE);
        login = request.getParameter(RequestParameterName.LOGIN);
        password = request.getParameter(RequestParameterName.PASSWORD);
        name = request.getParameter(RequestParameterName.NAME);
        surname = request.getParameter(RequestParameterName.SURNAME);
        email = request.getParameter(RequestParameterName.EMAIL);
        career_start_year = Integer.parseInt(request.getParameter(RequestParameterName.CAREER_START_YEAR));

        try {

            result = service.registration(new User(role,login, password,name, surname, email, career_start_year ));
            request.setAttribute(RequestParameterName.RESULT_INFO, result);
            page = JSPPageName.RESULT_PAGE;

        } catch (ServiceException e) {
            page = JSPPageName.ERROR_PAGE;
        }
        return page;
    }
}
