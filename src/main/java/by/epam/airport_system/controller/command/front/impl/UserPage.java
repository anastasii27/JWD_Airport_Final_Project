package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.RequestParameterName;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import by.epam.airport_system.service.UserFlightsService;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Log4j2
public class UserPage implements Command {
    private static final String ANSWER = "local.message.user_page.1";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User)request.getSession().getAttribute(RequestParameterName.USER);
        UserFlightsService userFlightsService = ServiceFactory.getInstance().getUserFlightsService();
        List<Flight> nearestFlights;
        String surname = "";
        String email = "";

        if(user!= null) {
            surname = user.getSurname();
            email = user.getEmail();
        }
        try {
            nearestFlights = userFlightsService.nearestUserFlights(surname, email);

            if(nearestFlights.size() != 0) {
                request.setAttribute(RequestParameterName.FLIGHT, nearestFlights);
            }else{
                request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            }

            forwardTo(request,response, JSPPageName.USER_PAGE);
        } catch (ServiceException e) {
            log.error("Cannot execute command for user page", e);
            errorPage(response);
        }
    }
}
