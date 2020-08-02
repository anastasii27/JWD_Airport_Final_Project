package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.User;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Log4j2
public class CreateFlight implements Command {
    private static final String ANSWER = "local.message.create_flight.1";
    private static final String FREE_CREWS_PAGE_PATH = "/airport?action=free_crews_for_flight";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);

        try {
            List<String> validationResults = initialValidation(request, response);

            if(validationResults.size() == 0){
                String flightNumber = request.getParameter(ParameterName.FLIGHT_NUMBER);
                String dispatcherFullName = request.getParameter(ParameterName.USER);
                String plane = request.getParameter(ParameterName.PLANE);
                LocalDate departureDate = LocalDate.parse(request.getParameter(ParameterName.DEPARTURE_DATE));
                LocalTime departureTime = LocalTime.parse(request.getParameter(ParameterName.DEPARTURE_TIME));
                String departureCountry = request.getParameter(ParameterName.DEPARTURE_COUNTRY);
                String departureCityWithAirport = request.getParameter(ParameterName.DEPARTURE_AIRPORT);
                LocalDate destinationDate = LocalDate.parse(request.getParameter(ParameterName.DESTINATION_DATE));
                LocalTime destinationTime = LocalTime.parse(request.getParameter(ParameterName.DESTINATION_TIME));
                String destinationCountry = request.getParameter(ParameterName.DESTINATION_COUNTRY);
                String destinationCityWithAirport = request.getParameter(ParameterName.DESTINATION_AIRPORT);

                User dispatcher = User.builder().name(extractName(dispatcherFullName))
                                                .surname(extractSurname(dispatcherFullName)).build();

                Flight flight = Flight.builder().flightNumber(flightNumber)
                                                .dispatcher(dispatcher)
                                                .planeNumber(extractPlaneNumber(plane))
                                                .departureDate(departureDate)
                                                .departureTime(departureTime)
                                                .departureCountry(departureCountry)
                                                .departureAirportShortName(extractAirportName(departureCityWithAirport))
                                                .destinationDate(destinationDate)
                                                .destinationTime(destinationTime)
                                                .destinationCountry(destinationCountry)
                                                .destinationAirportShortName(extractAirportName(destinationCityWithAirport)).build();

                FlightService flightService = ServiceFactory.getInstance().getFlightService();
                boolean operationResult = flightService.createFlight(flight);

                if(operationResult){
                    session.setAttribute(ParameterName.FLIGHT, flight);
                    response.sendRedirect(request.getContextPath()+ FREE_CREWS_PAGE_PATH);
                }else {
                    String language = String.valueOf(session.getAttribute(ParameterName.LOCAL));
                    ResponseMessageManager resourceManager = new ResponseMessageManager(language);

                    session.setAttribute(ParameterName.RESULT_INFO,resourceManager.getValue(ANSWER));
                    response.sendRedirect(JSPPageName.RESULT_PAGE);
                }
            }
        } catch(ServiceException | IOException e){
                log.error("Cannot execute command for flight creating", e);
                errorPage(response);
        }
    }

    private List<String> initialValidation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Validator validator = ValidationFactory.getInstance().getFlightValidation();

        Map<String, String> params = RequestToMapParser.toRequestParamsMap(request);
        ValidationResult result = validator.validate(params);

        if(!result.isEmpty()){
            request.getSession().setAttribute(ParameterName.RESULT_INFO, result.getErrorsList());
            response.sendRedirect(JSPPageName.RESULT_PAGE);
        }
        return result.getErrorsList();
    }
}
