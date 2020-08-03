package by.epam.airport_system.controller.command.ajax.impl;

import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.service.CrewService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class CrewNameExistence implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CrewService crewService = ServiceFactory.getInstance().getCrewService();

        String crewName = request.getParameter(ParameterName.CREW_NAME);

        try {
            boolean operationResult = crewService.doesCrewExist(crewName);

            response.getWriter().write(String.valueOf(!operationResult));
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for crew name check", e);
        }
    }
}
