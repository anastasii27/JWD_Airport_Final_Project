package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.controller.util.RequestToMapParser;
import by.epam.airport_system.controller.util.ResponseMessageManager;
import by.epam.airport_system.service.FlightService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import by.epam.airport_system.service.validation.ValidationFactory;
import by.epam.airport_system.service.validation.ValidationResult;
import by.epam.airport_system.service.validation.Validator;
import lombok.extern.log4j.Log4j2;
import static by.epam.airport_system.controller.util.RequestParametersExtractor.*;
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
    private final static String ANSWER = "local.message.edit_flight.fail";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        FlightService flightService = ServiceFactory.getInstance().getFlightService();

        try {
            List<String> validationResults = initialValidation(request, response);

            if(validationResults.size() == 0) {
                String flightId = request.getParameter(ParameterName.ID);
                String plane = request.getParameter(ParameterName.PLANE);
                String crew = request.getParameter(ParameterName.CREWS);
                String status = request.getParameter(ParameterName.STATUS);
                String departureDate = request.getParameter(ParameterName.DEPARTURE_DATE);
                String destinationDate = request.getParameter(ParameterName.DESTINATION_DATE);
                String departureTime = request.getParameter(ParameterName.DEPARTURE_TIME);
                String destinationTime = request.getParameter(ParameterName.DESTINATION_TIME);
                String departureAirport = request.getParameter(ParameterName.DEPARTURE_AIRPORT);
                String destinationAirport = request.getParameter(ParameterName.DESTINATION_AIRPORT);

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

                String path;
                if (operationResult) {
                    path = request.getContextPath()+ TO_ADMIN_MAIN_PAGE + LocalDate.now();
                } else {
                    String language = String.valueOf(request.getSession().getAttribute(ParameterName.LOCAL));
                    ResponseMessageManager responseManager = new ResponseMessageManager(language);

                    request.getSession().setAttribute(ParameterName.RESULT_INFO,responseManager.getValue(ANSWER));
                    path = JSPPageName.RESULT_PAGE;
                }
                response.sendRedirect(path);
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
            request.getSession().setAttribute(ParameterName.RESULT_INFO, result.getErrorsList());
            response.sendRedirect(JSPPageName.RESULT_PAGE);
        }
        return result.getErrorsList();
    }
}
