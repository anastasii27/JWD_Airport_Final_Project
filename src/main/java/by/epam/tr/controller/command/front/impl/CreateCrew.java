package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateCrew implements Command {//validation с сообщением

    private Logger logger = LogManager.getLogger(getClass());
    private static final String ANSWER = "Crew was successfully created";
    private static final String PATH = "/jsp/result.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        HttpSession session = request.getSession(true);
        String crewName;
        String planeCommander;
        String secondPilot;
        String [] stewards;
        Map <String,User> members;
        boolean operationResult;

        crewName = request.getParameter(RequestParameterName.CREW_NAME);
        planeCommander = request.getParameter(RequestParameterName.PILOT1);
        secondPilot = request.getParameter(RequestParameterName.PILOT2);
        stewards =  request.getParameterValues(RequestParameterName.STEWARD);
        try {

            User commander = new User(userName(planeCommander), userSurname(planeCommander));
            User pilotNumberTwo = new User(userName(secondPilot), userSurname(secondPilot));

            members = usersList(stewards);
            members.put(RequestParameterName.PILOT1, commander);
            members.put(RequestParameterName.PILOT2, pilotNumberTwo);

            operationResult = crewService.createCrew(crewName, members);

            if(operationResult){
                session.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            }

            response.sendRedirect(request.getContextPath()+ PATH);
        } catch (ServiceException | IOException e) {
            logger.error("Cannot execute command for crew creation", e);
            errorPage(response);
        }
    }

    private Map<String, User> usersList(String [] usersStringArray){

        Map<String, User> users = new HashMap<>();
        User user;

        for (String s : usersStringArray) {
            if (s.length() != 0) {
                user = new User(userName(s), userSurname(s));
                users.put(RequestParameterName.STEWARD,user);
            }
        }
        return users;
    }

    private String userName(String fullUserName){

        return fullUserName.substring(0, fullUserName.indexOf(' ')).trim();
    }

    private String userSurname(String fullUserName){

        return fullUserName.substring(fullUserName.indexOf(' ')).trim();
    }
}
