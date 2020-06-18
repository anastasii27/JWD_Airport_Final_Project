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
import java.io.IOException;

public class CrewNameExistence implements Command {

    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        boolean operationResult;
        String crewName;

        crewName = request.getParameter(RequestParameterName.CREW_NAME);
        try {
            operationResult = crewService.doesCrewNameExist(crewName);

            response.getWriter().write(String.valueOf(!operationResult));
        } catch (ServiceException e) {
            logger.error("Cannot execute ajax command for crew name check", e);
        } catch (IOException e) {
            logger.error("Cannot write json to response", e);
        }
    }
}
