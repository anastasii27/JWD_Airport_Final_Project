package by.epam.airport_system.controller.command.ajax.impl;

import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.util.GsonConverter;
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
public class CrewMembers implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CrewMemberService crewMemberService = ServiceFactory.getInstance().getCrewMemberService();

        String crewName = request.getParameter(ParameterName.CREW_NAME);

        try {
            List crewList = crewMemberService.crewMembers(crewName);
            String crewGson = GsonConverter.convertToGson(crewList);

            response.getWriter().write(crewGson);
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for crew members getting", e);
        }
    }
}