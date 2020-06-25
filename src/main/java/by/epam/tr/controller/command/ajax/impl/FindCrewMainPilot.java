package by.epam.tr.controller.command.ajax.impl;

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
import java.io.IOException;

public class FindCrewMainPilot implements Command {//todo доделать
    private Logger LOGGER = LogManager.getLogger(getClass());

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
            LOGGER.error("Cannot execute ajax command for crew creation", e);
        } catch (IOException e) {
            LOGGER.error("Cannot write json to response", e);
        }
    }
}
