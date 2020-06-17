package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.util.RequestToMapParser;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.validation.ValidationFactory;
import by.epam.tr.service.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CreateCrew implements Command {

    private Logger logger = LogManager.getLogger(getClass());
    private static final String ANSWER = "Crew was successfully created";
    private static final String PATH = "/jsp/result.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        HttpSession session = request.getSession(true);
        Validator validator = ValidationFactory.getInstance().getCrewValidation();
        boolean operationResult = false;

        String crewName;
        String firstPilot;
        String secondPilot;
        String [] stewards;

        crewName = request.getParameter(RequestParameterName.CREW_NAME);
        firstPilot = request.getParameter(RequestParameterName.PILOT1);
        secondPilot = request.getParameter(RequestParameterName.PILOT2);
        stewards =  request.getParameterValues(RequestParameterName.STEWARD);

        Map<String, String> crewForValidation = RequestToMapParser.crewMembersMapForValidation(request);
        List<String> validationResults = validator.validate(crewForValidation);

        try {
            if(validationResults.size()==0){
                Map<String, User> crew = RequestToMapParser.crewMembersMap(stewards, firstPilot, secondPilot);
                operationResult = crewService.createCrew(crewName, crew);
            }else {
                session.setAttribute(RequestParameterName.RESULT_INFO, validationResults);
            }

            if(operationResult){
                session.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            }

            response.sendRedirect(request.getContextPath()+ PATH);
        } catch (ServiceException | IOException e) {
            logger.error("Cannot execute command for crew creation", e);
            errorPage(response);
        }
    }
}
