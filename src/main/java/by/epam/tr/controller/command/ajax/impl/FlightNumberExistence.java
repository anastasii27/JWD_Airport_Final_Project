package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@Log4j2
public class FlightNumberExistence implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        String flightNumber;
        String date;

        flightNumber = request.getParameter(RequestParameterName.FLIGHT_NUMBER);
        date = request.getParameter(RequestParameterName.DATE);

        try {
            boolean operationResult = flightService.doesFlightNumberExist(flightNumber, LocalDate.parse(date));

            response.getWriter().write(String.valueOf(!operationResult));
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for flight number existence determining", e);
        } catch (IOException e) {
            log.error("Cannot write response", e);
        }
    }
}
