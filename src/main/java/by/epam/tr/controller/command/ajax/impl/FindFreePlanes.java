package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.bean.Plane;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.util.GSONConverter;
import by.epam.tr.controller.util.RequestParametersExtractor;
import by.epam.tr.service.PlaneService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Log4j2
public class FindFreePlanes implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        PlaneService planeService = ServiceFactory.getInstance().getPlaneService();
        List<Plane> planes;
        String cityWithAirport;
        String airportName;

        cityWithAirport = request.getParameter(RequestParameterName.DEPARTURE_AIRPORT);
        airportName = RequestParametersExtractor.airportName(cityWithAirport);
        try {
            planes = planeService.freePlanesAtAirport(airportName);

            String planesGSON = GSONConverter.convertListToGSON(planes);
            response.getWriter().write(planesGSON);
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for free planes finding", e);
        } catch (IOException e) {
            log.error("Cannot write json to response", e);
        }
    }
}
