package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import static by.epam.tr.controller.util.RequestToMapParser.*;
import by.epam.tr.controller.util.ResponseMessageManager;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.validation.ValidationFactory;
import by.epam.tr.service.validation.ValidationResult;
import by.epam.tr.service.validation.Validator;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Log4j2
public class CreateCrew implements Command {
    private static final String ANSWER = "local.message.create_crew.1";
    private static final String PATH = "/jsp/result.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        HttpSession session = request.getSession(true);
        String crewName = request.getParameter(RequestParameterName.CREW_NAME);

        Map<String, String> validationMap = toCrewMap(request);
        Validator validator = ValidationFactory.getInstance().getCrewValidation();
        ValidationResult validationResult = validator.validate(validationMap);

        try {
            boolean operationResult = false;

            if(validationResult.isEmpty()){
                Map<String, User> crew = toCrewMembersMap(request);
                operationResult = crewService.createCrew(crewName, crew);
            }else {
                session.setAttribute(RequestParameterName.RESULT_INFO, validationResult.getErrorsList());
            }

            if(operationResult){
                String language = String.valueOf(session.getAttribute(RequestParameterName.LOCAL));
                ResponseMessageManager resourceManager = new ResponseMessageManager(language);

                session.setAttribute(RequestParameterName.RESULT_INFO, resourceManager.getValue(ANSWER));
            }

            response.sendRedirect(request.getContextPath()+ PATH);
        } catch (ServiceException | IOException e) {
            log.error("Cannot execute command for crew creation", e);
            errorPage(response);
        }
    }
}
