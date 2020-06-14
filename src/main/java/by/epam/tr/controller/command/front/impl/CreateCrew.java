package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateCrew implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String p1 = request.getParameter("first_pilot");
        String p2 = request.getParameter("second_pilot");
        String [] s =  request.getParameterValues("steward");

    }
}
