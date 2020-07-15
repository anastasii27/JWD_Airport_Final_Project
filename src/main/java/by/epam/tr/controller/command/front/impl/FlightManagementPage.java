package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.FlightService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Log4j2
public class FlightManagementPage implements Command {
    private static final String ANSWER = "Sorry, no flights were found";
    private final static String CURRENT_PAGE_PATH = "/airport?action=show_flight_management_page&departure_date=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        FlightService userFlightsService = ServiceFactory.getInstance().getFlightService();
        HttpSession session = request.getSession(true);
        String departureDate;
        List<Flight> flights;

        departureDate = request.getParameter(RequestParameterName.DEPARTURE_DATE);
        try {
            flights = userFlightsService.allFlightByDay(LocalDate.parse(departureDate));

            if(flights.size()!= 0){
                flights.sort(Flight.SORT_BY_TIME_AND_DATE);
                request.setAttribute(RequestParameterName.FLIGHT, flights);
            }else {
                request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            }
            request.setAttribute(RequestParameterName.DEPARTURE_DATE, departureDate);
            session.setAttribute(RequestParameterName.PREVIOUS_PAGE, request.getContextPath()+ CURRENT_PAGE_PATH + LocalDate.now());

            forwardTo(request,response, JSPPageName.FLIGHT_MANAGEMENT_PAGE);
        } catch (ServiceException e) {
            log.error("Cannot execute command for admin flights", e);
            errorPage(response);
        }
    }
}
