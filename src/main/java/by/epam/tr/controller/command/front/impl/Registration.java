package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.util.RequestToMapParser;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.UserService;
import by.epam.tr.service.validation.ValidationFactory;
import by.epam.tr.service.validation.Validator;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Log4j2
public class Registration implements Command{
    private final static String ANSWER = "You are registered";
    private final static String CURRENT_PAGE_PATH = "/airport?action=show_register_page";
    private final static int LOG_ROUNDS = 12;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService service = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(true);
        Validator validator = ValidationFactory.getInstance().getRegistrationValidation();

        String role = request.getParameter(RequestParameterName.ROLE);
        String login = request.getParameter(RequestParameterName.LOGIN);
        String password = request.getParameter(RequestParameterName.PASSWORD);
        String name = request.getParameter(RequestParameterName.NAME);
        String surname = request.getParameter(RequestParameterName.SURNAME);
        String email = request.getParameter(RequestParameterName.EMAIL);
        String careerStartYear = request.getParameter(RequestParameterName.CAREER_START_YEAR);

        Map<String, String> params = RequestToMapParser.toRequestParamsMap(request);
        List<String> validationResults = validator.validate(params);

        try {
            boolean result = false;
            if(validationResults.size()==0){
                User user =  User.builder().role(role)
                                            .login(login)
                                            .password(hashPassword(password))
                                            .name(name)
                                            .surname(surname)
                                            .email(email)
                                            .careerStartYear(careerStartYear).build();

                result = service.signUpUser(user);
            }else {
                session.setAttribute(RequestParameterName.RESULT_INFO, validationResults);
            }

            if(result){
                session.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            }

            session.setAttribute(RequestParameterName.PREVIOUS_PAGE, request.getContextPath()+ CURRENT_PAGE_PATH);
            response.sendRedirect(JSPPageName.RESULT_PAGE);
        } catch (ServiceException| IOException e) {
            log.error("Cannot execute command for registration", e);
            errorPage(response);
        }
    }

    private String hashPassword(String password) {
        String salt = BCrypt.gensalt(LOG_ROUNDS);

        return BCrypt.hashpw(password, salt);
    }
}

