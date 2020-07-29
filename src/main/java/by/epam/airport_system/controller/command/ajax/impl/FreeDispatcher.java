package by.epam.airport_system.controller.command.ajax.impl;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.controller.util.GsonConverter;
import by.epam.airport_system.controller.util.RequestParametersExtractor;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import by.epam.airport_system.service.UserService;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Log4j2
public class FreeDispatcher implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = ServiceFactory.getInstance().getUserService();

        String cityWithAirport = request.getParameter(ParameterName.CITY_WITH_AIRPORT);
        String airportName = RequestParametersExtractor.airportName(cityWithAirport);
        LocalDate date = LocalDate.parse(request.getParameter(ParameterName.DATE));
        LocalTime time = LocalTime.parse(request.getParameter(ParameterName.TIME));
        try {
            List<User> freeDispatchers = userService.freeDispatchers(date, time, airportName);

            String freeDispatchersGson = GsonConverter.convertToGson(freeDispatchers);
            response.getWriter().write(freeDispatchersGson);
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for free dispatcher searching", e);
        } catch (IOException e) {
            log.error("Cannot write json to response", e);
        }
    }
}
