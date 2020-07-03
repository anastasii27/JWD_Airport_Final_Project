package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.bean.Flight;
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
public class SetCrewForFlight implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        Flight flight = (Flight)request.getSession().getAttribute(RequestParameterName.FLIGHT);
        String crewName;

        crewName = request.getParameter(RequestParameterName.CREW_NAME);
        try {
            if(flight!= null){
                boolean operationResult = crewService.setCrewForFlight(crewName, flight.getFlightNumber());
                response.getWriter().write(String.valueOf(operationResult));
                request.getSession().removeAttribute(RequestParameterName.FLIGHT);
            }else {
                response.getWriter().write("false");
            }
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for crew to flight setting", e);
        } catch (IOException e) {
            log.error("Cannot write json to response", e);
        }
    }
}
