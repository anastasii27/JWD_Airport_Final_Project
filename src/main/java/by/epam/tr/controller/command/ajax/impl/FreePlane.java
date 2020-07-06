package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.Plane;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.util.GsonConverter;
import by.epam.tr.controller.util.RequestParametersExtractor;
import by.epam.tr.service.PlaneService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Log4j2
public class FreePlane implements Command {//todo valid

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        PlaneService planeService = ServiceFactory.getInstance().getPlaneService();
        List<Plane> planes;
        String cityWithAirport;
        String airportName;
        String departureDate;

        cityWithAirport = request.getParameter(RequestParameterName.DEPARTURE_AIRPORT);
        departureDate = request.getParameter(RequestParameterName.DEPARTURE_DATE);
        airportName = RequestParametersExtractor.airportName(cityWithAirport);

        Flight flight = Flight.builder().departureAirportShortName(airportName)
                                        .departureDate(LocalDate.parse(departureDate)).build();

        try {
            planes = planeService.freePlanesForFlight(flight);

            String planesGson = GsonConverter.convertToGson(planes);
            response.getWriter().write(planesGson);
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for free planes searching", e);
        } catch (IOException e) {
            log.error("Cannot write json to response", e);
        }
    }
}
