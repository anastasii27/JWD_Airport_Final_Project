package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.controller.JSPPageName;
import by.epam.tr.controller.RequestParameterName;
import by.epam.tr.controller.command.Command;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.UserService;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class SingIn implements Command {

    private final static String RESULT = "true";

    @Override
    public String execute(HttpServletRequest request) {

        UserService service = ServiceFactory.getInstance().getUserDAO();
        String result;
        String page;
        ArrayList<Flight> flight;

        String login = request.getParameter(RequestParameterName.LOGIN);
        String password = request.getParameter(RequestParameterName.PASSWORD);

        try {
            result = service.singIn(login, password);
            flight = service.flightsInfo();

            if(result.equals(RESULT)){
                request.setAttribute(RequestParameterName.FLIGHT_INFO, flight);
                page = JSPPageName.HOME_PAGE;
            }else {
                request.setAttribute(RequestParameterName.RESULT_INFO, result);
                page = JSPPageName.RESULT_PAGE;
            }

        } catch (ServiceException e) {
            page = JSPPageName.ERROR_PAGE;
        }
        return page;
    }
}
