package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
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
public class EditUser implements Command {
    private final static String KEY1 = "local.user.edit_success";
    private final static String KEY2 = "local.user.edit_fail";
    private final static String CURRENT_PAGE_PATH = "/airport?action=show_user_editing_page";
    private final static String USER_PAGE_PATH = "/airport?action=show_user_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService service = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(true);
        Validator validator = ValidationFactory.getInstance().getUserValidation();

        String name = request.getParameter(ParameterName.NAME);
        String surname = request.getParameter(ParameterName.SURNAME);
        String role = request.getParameter(ParameterName.ROLE);
        String email = request.getParameter(ParameterName.EMAIL);
        String careerStartYear = request.getParameter(ParameterName.CAREER_START_YEAR);
        String login = request.getParameter(ParameterName.LOGIN);
        String id = request.getParameter(ParameterName.ID);

        Map<String, String> params = RequestToMapParser.toRequestParamsMap(request);
        ValidationResult validationResult = validator.validate(params);

        try {
            session.setAttribute(ParameterName.PREVIOUS_PAGE, request.getContextPath() + CURRENT_PAGE_PATH);

            if(!validationResult.isEmpty()){
                session.setAttribute(ParameterName.RESULT_INFO, validationResult.getErrorsList());
            }else {
                User user = User.builder().id(Integer.parseInt(id))
                            .login(login)
                            .name(name)
                            .surname(surname)
                            .email(email)
                            .role(role)
                            .careerStartYear(careerStartYear).build();

                boolean operationResult = service.editUser(user);
                String language = String.valueOf(session.getAttribute(ParameterName.LOCAL));
                session.setAttribute(ParameterName.RESULT_INFO, getResultMessage(operationResult, language));

                if(operationResult) {
                    session.setAttribute(ParameterName.USER, user);
                    session.setAttribute(ParameterName.PREVIOUS_PAGE, request.getContextPath() + USER_PAGE_PATH);
                }
            }

            response.sendRedirect(JSPPageName.RESULT_PAGE);
        } catch (ServiceException | IOException e) {
            log.error("Cannot execute command for user editing", e);
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
