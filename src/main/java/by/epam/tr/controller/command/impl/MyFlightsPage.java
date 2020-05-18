package by.epam.tr.controller.command.impl;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFlightsPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        forwardTo(request,response, JSPPageName.MY_FLIGHTS_PAGE);
    }
}
