package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class FindCrewMainPilot implements Command {//todo доделать

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
            log.error("Cannot execute ajax command for crew creation", e);
        } catch (IOException e) {
            log.error("Cannot write json to response", e);
        }
    }
}
