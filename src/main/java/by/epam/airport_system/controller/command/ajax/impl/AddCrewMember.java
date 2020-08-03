package by.epam.airport_system.controller.command.ajax.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.Command;
import static by.epam.airport_system.controller.util.RequestParametersExtractor.*;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.service.CrewMemberService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Log4j2
public class AddCrewMember implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CrewMemberService crewMemberService = ServiceFactory.getInstance().getCrewMemberService();

        String crewName = request.getParameter(ParameterName.CREW_NAME);
        String crewMembers = request.getParameter(ParameterName.USER);

        List<User> membersList = usersList(crewMembers);

        try {
            boolean operationResult = crewMemberService.addCrewMember(crewName, membersList);
            response.getWriter().write(String.valueOf(operationResult));
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for crew member adding", e);
        }
    }
}
