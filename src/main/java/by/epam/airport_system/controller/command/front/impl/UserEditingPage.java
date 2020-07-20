package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserEditingPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        forwardTo(request, response, JSPPageName.USER_EDITING_PAGE);
    }
}
