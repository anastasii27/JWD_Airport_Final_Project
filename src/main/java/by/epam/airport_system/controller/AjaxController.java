package by.epam.airport_system.controller;

import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.command.ajax.AjaxCommandProvider;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ajax")
public class AjaxController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter(ParameterName.COMMAND);
        Command command = AjaxCommandProvider.getInstance().getCommand(commandName);

        command.execute(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
