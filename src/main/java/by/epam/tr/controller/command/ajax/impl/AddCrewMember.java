package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import static by.epam.tr.controller.util.RequestParametersExtractor.*;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.CrewMemberService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Log4j2
public class AddCrewMember implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CrewMemberService crewMemberService = ServiceFactory.getInstance().getCrewMemberService();
        String crewName;
        String crewMembers;
        List<User> membersList;

        crewName = request.getParameter(RequestParameterName.CREW_NAME);
        crewMembers = request.getParameter(RequestParameterName.USER);

        membersList = usersListFromFullNamesString(crewMembers);
        try {
            boolean operationResult = crewMemberService.addCrewMember(crewName, membersList);

            response.getWriter().write(String.valueOf(operationResult));
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for crew member adding", e);
        } catch (IOException e) {
            log.error("Cannot write response", e);
        }
    }
}
