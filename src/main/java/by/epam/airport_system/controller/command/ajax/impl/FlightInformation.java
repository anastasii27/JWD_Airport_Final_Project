package by.epam.airport_system.controller.command.ajax.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.controller.util.GsonConverter;
import by.epam.airport_system.service.CrewService;
import by.epam.airport_system.service.FlightService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@Log4j2
public class FlightInformation implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        String flightGson = "";

        String flightNumber = request.getParameter(ParameterName.FLIGHT_NUMBER);
        String departureDate = request.getParameter(ParameterName.DEPARTURE_DATE);

        try {
            Flight flight = flightService.flightInfo(flightNumber, LocalDate.parse(departureDate));

            if (flight != null) {
                String flightCrew = crewService.flightCrew(flight);
                flight.setCrew(flightCrew);
                flightGson = GsonConverter.convertToGson(flight);
            }

            response.getWriter().write(flightGson);
        } catch (ServiceException | IOException e) {
            log.error("Cannot execute ajax command for flight information getting", e);
        }
    }
}
