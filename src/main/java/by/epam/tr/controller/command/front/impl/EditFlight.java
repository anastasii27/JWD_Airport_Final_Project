package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.util.RequestToMapParser;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.validation.ValidationFactory;
import by.epam.tr.service.validation.ValidationResult;
import by.epam.tr.service.validation.Validator;
import lombok.extern.log4j.Log4j2;
import static by.epam.tr.controller.util.RequestParametersExtractor.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Log4j2
public class EditFlight implements Command {
    private final static String TO_ADMIN_MAIN_PAGE = "/airport?action=show_flight_management_page&departure_date=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        FlightService flightService = ServiceFactory.getInstance().getFlightService();

        try {
            List<String> validationResults = initialValidation(request, response);

            if(validationResults.size() == 0) {
                String flightId = request.getParameter(RequestParameterName.ID);
                String plane = request.getParameter(RequestParameterName.PLANE);
                String crew = request.getParameter(RequestParameterName.CREWS);
                String status = request.getParameter(RequestParameterName.STATUS);
                String departureDate = request.getParameter(RequestParameterName.DEPARTURE_DATE);
                String destinationDate = request.getParameter(RequestParameterName.DESTINATION_DATE);
                String departureTime = request.getParameter(RequestParameterName.DEPARTURE_TIME);
                String destinationTime = request.getParameter(RequestParameterName.DESTINATION_TIME);
                String departureAirport = request.getParameter(RequestParameterName.DEPARTURE_AIRPORT);
                String destinationAirport = request.getParameter(RequestParameterName.DESTINATION_AIRPORT);

                Flight flight = Flight.builder().id(Integer.parseInt(flightId))
                                                .planeNumber(planeNumber(plane))
                                                .crew(crew)
                                                .status(status)
                                                .departureDate(LocalDate.parse(departureDate))
                                                .departureTime(LocalTime.parse(departureTime))
                                                .destinationDate(LocalDate.parse(destinationDate))
                                                .destinationTime(LocalTime.parse(destinationTime))
                                                .departureAirportShortName(airportName(departureAirport))
                                                .destinationAirportShortName(airportName(destinationAirport)).build();

                boolean operationResult = flightService.editFlight(flight);

                if (operationResult) {
                    response.sendRedirect(request.getContextPath()+ TO_ADMIN_MAIN_PAGE + LocalDate.now());
                } else {
                    request.getSession().setAttribute(RequestParameterName.RESULT_INFO,"No");
                    response.sendRedirect(JSPPageName.RESULT_PAGE);
                }
            }
        } catch (ServiceException | IOException e) {
            log.error("Cannot execute command for flight editing", e);
            errorPage(response);
        }

    }

    private List<String> initialValidation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Validator validator = ValidationFactory.getInstance().getEditFlightValidation();

        Map<String, String> params = RequestToMapParser.toRequestParamsMap(request);
        ValidationResult result = validator.validate(params);

        if(!result.isEmpty()){
            request.getSession().setAttribute(RequestParameterName.RESULT_INFO, result.getErrorsList());
            response.sendRedirect(JSPPageName.RESULT_PAGE);
        }
        return result.getErrorsList();
    }
}
