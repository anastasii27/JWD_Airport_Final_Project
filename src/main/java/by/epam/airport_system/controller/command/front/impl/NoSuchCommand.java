package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.controller.command.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoSuchCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        errorPage(response);
    }
}
