package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCrew implements Command {

    private Logger logger = LogManager.getLogger(getClass());
    //todo продумать удаление с рейса
    //todo продумать возврат ответа
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        String crewName;

        crewName = request.getParameter(RequestParameterName.CREW_NAME);
        try {
            crewService.deleteCrew(crewName);

        } catch (ServiceException e) {
            logger.error("Cannot execute ajax command for crew deleting", e);
        }
    }
}
