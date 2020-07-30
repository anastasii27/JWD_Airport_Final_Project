package by.epam.airport_system.controller;

import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.command.front.CommandProvider;
import by.epam.airport_system.controller.constant_parameter.ParameterName;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/airport")
public class FrontController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter(ParameterName.ACTION);
        Command command = CommandProvider.getInstance().getCommand(commandName);

        command.execute(req, resp);
    }
}
