package by.epam.airport_system.controller.command.ajax.impl;

import by.epam.airport_system.bean.Flight;
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
public class SetCrewForFlight implements Command {
    private static final String OPERATION_FAIL = "false";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        Flight flight = (Flight)request.getSession().getAttribute(ParameterName.FLIGHT);

        String crewName = request.getParameter(ParameterName.CREW_NAME);

        try {
            if(flight!= null){
                boolean operationResult = crewService.setCrewForFlight(crewName, flight.getFlightNumber());

                response.getWriter().write(String.valueOf(operationResult));
                request.getSession().removeAttribute(ParameterName.FLIGHT);
            }else {
                response.getWriter().write(OPERATION_FAIL);
            }
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for crew to flight setting", e);
        }
    }
}
