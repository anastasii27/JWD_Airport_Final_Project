package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import static by.epam.airport_system.controller.util.RequestToMapParser.*;
import by.epam.airport_system.controller.util.ResponseMessageManager;
import by.epam.airport_system.service.CrewService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import by.epam.airport_system.service.validation.ValidationFactory;
import by.epam.airport_system.service.validation.ValidationResult;
import by.epam.airport_system.service.validation.Validator;
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
        String crewName = request.getParameter(ParameterName.CREW_NAME);

        Map<String, String> validationMap = toCrewMap(request);
        Validator validator = ValidationFactory.getInstance().getCrewValidation();
        ValidationResult validationResult = validator.validate(validationMap);

        try {
            boolean operationResult = false;

            if(validationResult.isEmpty()){
                Map<String, User> crew = toCrewMembersMap(request);
                operationResult = crewService.createCrew(crewName, crew);
            }else {
                session.setAttribute(ParameterName.RESULT_INFO, validationResult.getErrorsList());
            }

            if(operationResult){
                String language = String.valueOf(session.getAttribute(ParameterName.LOCAL));
                ResponseMessageManager resourceManager = new ResponseMessageManager(language);

                session.setAttribute(ParameterName.RESULT_INFO, resourceManager.getValue(ANSWER));
            }

            response.sendRedirect(request.getContextPath()+ PATH);
        } catch (ServiceException | IOException e) {
            log.error("Cannot execute command for crew creation", e);
            errorPage(response);
        }
    }
}
