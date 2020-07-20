package by.epam.airport_system.controller.command.ajax.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.RequestParameterName;
import by.epam.airport_system.service.CrewService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class CrewMainPilot implements Command {//todo доделать

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        String crewName;
        String commander = " ";

        crewName = request.getParameter(RequestParameterName.CREW_NAME);
        try {
            User user = crewService.findMainPilot(crewName);

            if(user != null) {
                 commander = user.getName() + " " + user.getSurname();
            }
            response.getWriter().write(commander);
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for crew main pilot searching", e);
        } catch (IOException e) {
            log.error("Cannot write response", e);
        }
    }
}
