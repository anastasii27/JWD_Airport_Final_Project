package by.epam.airport_system.controller.command.ajax.impl;

import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import by.epam.airport_system.controller.util.GsonConverter;
import by.epam.airport_system.service.CityService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Log4j2
public class CountryAirport implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CityService cityService = ServiceFactory.getInstance().getCityService();

        String country = request.getParameter(ParameterName.COUNTRY);

        try {
            List<String> countries = cityService.cityWithAirportList(country);

            String countriesGson = GsonConverter.convertToGson(countries);
            response.getWriter().write(countriesGson);
        } catch (ServiceException e) {
            log.error("Cannot execute ajax command for country airport searching", e);
        } catch (IOException e) {
            log.error("Cannot write json to response", e);
        }
    }
}

