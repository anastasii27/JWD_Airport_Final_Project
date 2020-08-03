package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.controller.util.ResponseMessageManager;
import by.epam.airport_system.service.FlightService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Log4j2
public class FlightManagementPage implements Command {
    private static final String ANSWER = "local.message.disp_flights.1";
    private final static String CURRENT_PAGE_PATH = "/airport?action=show_flight_management_page&departure_date=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        FlightService userFlightsService = ServiceFactory.getInstance().getFlightService();
        HttpSession session = request.getSession(true);

        String departureDate = request.getParameter(ParameterName.DEPARTURE_DATE);
        try {
            List<Flight> flights = userFlightsService.allFlightByDay(LocalDate.parse(departureDate));

            if(flights.size()!= 0){
                flights.sort(Flight.SORT_BY_TIME_AND_DATE);
                request.setAttribute(ParameterName.FLIGHT, flights);
            }else {
                String language = String.valueOf(request.getSession().getAttribute(ParameterName.LOCAL));
                ResponseMessageManager resourceManager = new ResponseMessageManager(language);

                request.setAttribute(ParameterName.RESULT_INFO, resourceManager.getValue(ANSWER));
            }
            request.setAttribute(ParameterName.DEPARTURE_DATE, departureDate);
            session.setAttribute(ParameterName.PREVIOUS_PAGE, request.getContextPath()+ CURRENT_PAGE_PATH + LocalDate.now());

            forwardTo(request,response, JSPPageName.FLIGHT_MANAGEMENT_PAGE);
        } catch (ServiceException e) {
            log.error("Cannot execute command for admin flights", e);
            response.sendRedirect(JSPPageName.ERROR_PAGE);
        }
    }
}
