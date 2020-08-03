package by.epam.airport_system.controller.command.ajax.impl;

import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.service.FlightService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@Log4j2
public class DeleteFlight implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        FlightService flightService = ServiceFactory.getInstance().getFlightService();

        String flightNumber = request.getParameter(ParameterName.FLIGHT_NUMBER);
        String departureDate = request.getParameter(ParameterName.DEPARTURE_DATE);

        try{
            boolean operationResult = flightService.deleteFlight(flightNumber, LocalDate.parse(departureDate));

            response.getWriter().write(String.valueOf(operationResult));
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for flight deleting", e);
        }
    }
}
