package by.epam.airport_system.controller.command.ajax.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.service.CrewMemberService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.epam.airport_system.controller.util.RequestParametersExtractor.*;

@Log4j2
public class DeleteCrewMember implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CrewMemberService crewMemberService = ServiceFactory.getInstance().getCrewMemberService();

        String crewName = request.getParameter(ParameterName.CREW_NAME);
        String member = request.getParameter(ParameterName.USER);
        User user = User.builder().name(userName(member)).surname(userSurname(member)).build();
        try {
            boolean operationResult = crewMemberService.deleteCrewMember(crewName, user);

            response.getWriter().write(String.valueOf(operationResult));
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for crew member deleting", e);
        } catch (IOException e) {
            log.error("Cannot write response", e);
        }
    }
}
