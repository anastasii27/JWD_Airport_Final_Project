package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.CrewMemberService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.epam.tr.controller.util.RequestParametersExtractor.*;

public class DeleteCrewMember implements Command {
    private Logger LOGGER = LogManager.getLogger(getClass());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CrewMemberService crewMemberService = ServiceFactory.getInstance().getCrewMemberService();
        String crewName;
        String member;
        User user;
        boolean operationResult;

        crewName = request.getParameter(RequestParameterName.CREW_NAME);
        member = request.getParameter(RequestParameterName.USER);
        user = new User(userName(member), userSurname(member));
        try {
            operationResult = crewMemberService.deleteCrewMember(crewName, user);

            response.getWriter().write(String.valueOf(operationResult));
        } catch (ServiceException e) {
            LOGGER.error("Cannot execute ajax command for crew member deleting", e);
        } catch (IOException e) {
            LOGGER.error("Cannot write response", e);
        }
    }
}
