package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Log4j2
public class FreeCrewsForFlight implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        Flight flight = (Flight)request.getSession().getAttribute(RequestParameterName.FLIGHT);

        try {
            if(flight !=  null) {
                Set<String> freeCrews = crewService.findFreeCrewsForFlight(flight);

                request.setAttribute(RequestParameterName.CREW, freeCrews);
                forwardTo(request, response, JSPPageName.FREE_CREWS_FOR_FLIGHT);

                request.getSession().removeAttribute(RequestParameterName.FLIGHT);
            }else {
                //
            }
        } catch (ServiceException e) {
            log.error("Cannot execute command for free flight searching", e);
        }
    }
}
