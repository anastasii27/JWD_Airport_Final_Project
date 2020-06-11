package by.epam.tr.controller;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.command.front.CommandProvider;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/airport")
public class FrontController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String commandName = req.getParameter(RequestParameterName.ACTION);
        Command command = CommandProvider.getInstance().getCommand(commandName);

        command.execute(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

}
