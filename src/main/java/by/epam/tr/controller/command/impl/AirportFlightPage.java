package by.epam.tr.controller.command.impl;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AirportFlightPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        forwardTo(request,response, JSPPageName.FLIGHTS_PAGE);
    }
}
