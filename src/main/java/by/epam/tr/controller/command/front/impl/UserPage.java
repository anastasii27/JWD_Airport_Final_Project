package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.UserFlightsService;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
