package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.command.Command;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.UserService;
import by.epam.tr.service.validation.ValidationFactory;
import by.epam.tr.service.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Registration implements Command{

    private Logger logger = LogManager.getLogger(getClass());
    private Map<String, String> params = new HashMap<>();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        UserService service = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(true);
        Validator validator = ValidationFactory.getInstance().getRegistrationValidation();

        boolean result = false;
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

        User user = new User(role,name, surname, email, careerStartYear);

        params = createMap(user, login, password);
        List <String> validationResults = validator.validate(params);

        try {

            if(validationResults.size()==0){
                result = service.userRegistration(user, login, password);
            }else {
                session.setAttribute(RequestParameterName.RESULT_INFO, validationResults);
            }

            if(result){
                session.setAttribute(RequestParameterName.RESULT_INFO, "Yes");//убрать
            }

            response.sendRedirect(JSPPageName.RESULT_PAGE);
        } catch (ServiceException| IOException e) {
            logger.error("Cannot execute command for registration", e);
            errorPage(response);
        }
    }

    private Map<String, String> createMap(User user, String login, String password){

        Map<String, String> params =  new HashMap<>();

        params.put("name", user.getName());
        params.put("surname", user.getSurname());
        params.put("email", user.getEmail());
        params.put("careerStartYear", user.getCareerStartYear());
        params.put("login", login);
        params.put("password", password);

        return params;
    }
}
