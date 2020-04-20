package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RegistrationAnswer;
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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        UserService service = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(true);
        RegistrationAnswer answer = RegistrationAnswer.getInstance();

        int result;
        String role;
        String login;
        String password;
        String name;
        String surname;
        String email;
        String careerStartYear;

        role = request.getParameter(RequestParameterName.ROLE);
        login = request.getParameter(RequestParameterName.LOGIN);
        password = request.getParameter(RequestParameterName.PASSWORD);
        name = request.getParameter(RequestParameterName.NAME);
        surname = request.getParameter(RequestParameterName.SURNAME);
        email = request.getParameter(RequestParameterName.EMAIL);
        careerStartYear = request.getParameter(RequestParameterName.CAREER_START_YEAR);

        try {

            result = service.registration(new User(role,name, surname, email, careerStartYear), login, password);

            session.setAttribute(RequestParameterName.RESULT_INFO, answer.getAnswer(result));
            response.sendRedirect(JSPPageName.RESULT_PAGE);

        } catch (ServiceException| IOException e) {
           errorPage(response);
        }
    }
}
