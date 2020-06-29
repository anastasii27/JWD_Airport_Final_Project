package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.controller.util.GsonConverter;
import by.epam.tr.controller.util.RequestParametersExtractor;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import by.epam.tr.service.UserService;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Log4j2
public class FindFreeDispatcher implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = ServiceFactory.getInstance().getUserService();
        String cityWithAirport;
        String airportName;
        LocalDate date;
        LocalTime time;

        cityWithAirport = request.getParameter(RequestParameterName.CITY_WITH_AIRPORT);
        airportName = RequestParametersExtractor.airportName(cityWithAirport);
        date = LocalDate.parse(request.getParameter(RequestParameterName.DATE));
        time = LocalTime.parse(request.getParameter(RequestParameterName.TIME));
        try {
            List<User> freeDispatchers = userService.freeDispatchers(date, time, airportName);

            String freeDispatchersGson = GsonConverter.convertListToGSON(freeDispatchers);
            response.getWriter().write(freeDispatchersGson);
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for free dispatcher searching", e);
        } catch (IOException e) {
            log.error("Cannot write json to response", e);
        }
    }
}
