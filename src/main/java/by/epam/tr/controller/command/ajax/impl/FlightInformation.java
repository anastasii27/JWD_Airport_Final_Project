package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.util.GsonConverter;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class FlightInformation implements Command {//todo продумать если нет инфы

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        FlightService flightService = ServiceFactory.getInstance().getFlightService();
        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        String flightNumber;
        String departureDate;
        String flightGson = "";

        flightNumber = request.getParameter(RequestParameterName.FLIGHT_NUMBER);
        departureDate = request.getParameter(RequestParameterName.DEPARTURE_DATE);
        try {
            Flight flight = flightService.flightInfo(flightNumber, departureDate);

            if (flight != null) {
                String flightCrew = crewService.flightCrew(flight);
                flight.setCrew(flightCrew);
                flightGson = GsonConverter.convertToGson(flight);
            }

            response.getWriter().write(flightGson);
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for flight information getting", e);
        } catch (IOException e) {
            log.error("Cannot write json to response", e);
        }
    }
}
