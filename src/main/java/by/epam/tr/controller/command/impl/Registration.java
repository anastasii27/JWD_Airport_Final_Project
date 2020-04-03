package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.command.Command;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Registration implements Command{

    private final static String ANSWER1 ="You are successfully registered";
    private final static String ANSWER2 ="This user is already exist";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        UserService service = ServiceFactory.getInstance().getUserDAO();
        HttpSession session = request.getSession(true);
        boolean result;
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

            result = service.registration(new User(role,name, surname, email, career_start_year), login, password);

            if(result){
                session.setAttribute(RequestParameterName.RESULT_INFO, ANSWER1);
            }else{
                session.setAttribute(RequestParameterName.RESULT_INFO, ANSWER2);
            }

            page = JSPPageName.RESULT_PAGE;

            response.sendRedirect(page);

        } catch (ServiceException| IOException e) {
           errorPage(response);
        }
    }
}
