package by.epam.tr.controller.command.front.impl;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FlightManagementPage implements Command {
    private Logger LOGGER = LogManager.getLogger(getClass());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        forwardTo(request,response, JSPPageName.FLIGHT_MANAGEMENT_PAGE);
    }
}
