package by.epam.airport_system.controller.command.ajax.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.controller.util.GsonConverter;
import by.epam.airport_system.service.CrewMemberService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class CrewMainPilot implements Command {
    private static final String NO_PILOT = "false";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CrewMemberService crewService = ServiceFactory.getInstance().getCrewMemberService();
        String crewName = request.getParameter(ParameterName.CREW_NAME);
        String commanderGson = NO_PILOT;

        try {
            User user = crewService.findCrewMainPilot(crewName);

            if(user != null) {
                commanderGson = GsonConverter.convertToGson(user);
            }

            response.getWriter().write(commanderGson);
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for crew main pilot searching", e);
        }
    }
}
