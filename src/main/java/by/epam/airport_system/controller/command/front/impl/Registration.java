package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.RequestParameterName;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.util.RequestToMapParser;
import by.epam.airport_system.controller.util.ResponseMessageManager;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import by.epam.airport_system.service.UserService;
import by.epam.airport_system.service.validation.ValidationFactory;
import by.epam.airport_system.service.validation.ValidationResult;
import by.epam.airport_system.service.validation.Validator;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class Registration implements Command{
    private final static String KEY1= "local.message.sign_up.success";
    private final static String KEY2= "local.message.sign_up.fail";
    private final static String CURRENT_PAGE_PATH = "/airport?action=show_register_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService service = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(true);
        Validator validator = ValidationFactory.getInstance().getCreatedUserValidation();

        String role = request.getParameter(RequestParameterName.ROLE);
        String login = request.getParameter(RequestParameterName.LOGIN);
        String password = request.getParameter(RequestParameterName.PASSWORD);
        String name = request.getParameter(RequestParameterName.NAME);
        String surname = request.getParameter(RequestParameterName.SURNAME);
        String email = request.getParameter(RequestParameterName.EMAIL);
        String careerStartYear = request.getParameter(RequestParameterName.CAREER_START_YEAR);

        Map<String, String> params = RequestToMapParser.toRequestParamsMap(request);
        ValidationResult validationResult = validator.validate(params);

        try {
            if(!validationResult.isEmpty()){
                session.setAttribute(RequestParameterName.RESULT_INFO, validationResult.getErrorsList());
            }else{
                User user =  User.builder().role(role)
                            .login(login)
                            .password(password)
                            .name(name)
                            .surname(surname)
                            .email(email)
                            .careerStartYear(careerStartYear).build();

                boolean operationResult = service.signUpUser(user);
                String language = String.valueOf(session.getAttribute(RequestParameterName.LOCAL));
                session.setAttribute(RequestParameterName.RESULT_INFO, getResultMessage(operationResult, language));
            }

            session.setAttribute(RequestParameterName.PREVIOUS_PAGE, request.getContextPath()+ CURRENT_PAGE_PATH);
            response.sendRedirect(JSPPageName.RESULT_PAGE);
        } catch (ServiceException| IOException e) {
            log.error("Cannot execute command for registration", e);
            errorPage(response);
        }
    }

    private String getResultMessage(boolean key, String language){
        Map<Boolean, String> answers = new HashMap<>();
        ResponseMessageManager resourceManager = new ResponseMessageManager(language);

        answers.put(true, resourceManager.getValue(KEY1));
        answers.put(false, resourceManager.getValue(KEY2));

        return answers.get(key);
    }
}

