package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateCrew implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        String crewName;
        boolean creationResult;

        crewName = request.getParameter(RequestParameterName.CREW_NAME);

        try {

            creationResult = crewService.createCrew(crewName);
            response.getWriter().write(String.valueOf(creationResult));

        } catch (ServiceException | IOException e) {
           //
        }
    }
}
