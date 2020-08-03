package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import static by.epam.airport_system.controller.util.RequestParametersExtractor.extractName;
import static by.epam.airport_system.controller.util.RequestParametersExtractor.extractSurname;
import static by.epam.airport_system.controller.util.RequestToMapParser.*;
import by.epam.airport_system.controller.util.ResponseMessageManager;
import by.epam.airport_system.service.CrewService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import by.epam.airport_system.service.validation.ValidationFactory;
import by.epam.airport_system.service.validation.ValidationResult;
import by.epam.airport_system.service.validation.Validator;
import lombok.extern.log4j.Log4j2;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class CreateCrew implements Command {
    private static final String ANSWER = "local.message.create_crew.1";
    private static final String PATH = "/jsp/result.jsp";
    private int count = 1;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        HttpSession session = request.getSession(true);
        String crewName = request.getParameter(ParameterName.CREW_NAME);

        Map<String, String> validationMap = toMapForValidation(request);
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
        } catch (ServiceException e) {
            log.error("Cannot execute command for crew creation", e);
            response.sendRedirect(JSPPageName.ERROR_PAGE);
        }
    }

    private Map<String, String> toMapForValidation(ServletRequest request){
        String key;
        Map<String, String> params = new HashMap<>();
        Enumeration<String> keys = request.getParameterNames();

        while (keys.hasMoreElements()){
            key = keys.nextElement();

            if(key.equals(ParameterName.PILOT) || key.equals(ParameterName.STEWARD)){
                putParametersArrayToMap(request.getParameterValues(key), params);
            }else {
                params.put(key, request.getParameter(key));
            }
        }
        addLanguageValueToMap(request, params);

        return params;
    }

    private void putParametersArrayToMap(String [] crewMembers, Map<String, String> users){
        for (String member: crewMembers) {
            if (member.length()!= 0) {
                users.put(ParameterName.USER+count++, member);
            }
        }
    }

    private Map<String,User> toCrewMembersMap(ServletRequest request){
        String key;
        Map<String, User> usersMap =  new HashMap<>();
        Enumeration<String> keys = request.getParameterNames();

        while (keys.hasMoreElements()){
            key = keys.nextElement();

            if(key.equals(ParameterName.PILOT) || key.equals(ParameterName.STEWARD)){
                putUsersArrayToMap(request.getParameterValues(key), usersMap);
            }
            if(key.equals(ParameterName.PILOT1)) {
                putUserToMap(request.getParameter(key), key, usersMap);
            }
        }

        return usersMap;
    }

    private void putUserToMap(String user, String key, Map<String, User> usersMap){
        User firstPilot = User.builder().name(extractName(user))
                                        .surname(extractSurname(user)).build();
        usersMap.put(key, firstPilot);
    }

    private void putUsersArrayToMap(String [] users, Map<String, User> usersMap){
        User user;
        for (String member: users) {
            if (member.length() != 0) {
                user = User.builder().name(extractName(member))
                                    .surname(extractSurname(member)).build();
                usersMap.put(ParameterName.USER+count++,user);
            }
        }
    }
}
