package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
public class UserPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        forwardTo(request, response, JSPPageName.USER_PAGE);
    }
}
