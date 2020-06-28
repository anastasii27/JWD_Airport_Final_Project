package by.epam.tr.controller;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.command.ajax.AjaxCommandProvider;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
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
        String commandName = req.getParameter(RequestParameterName.COMMAND);
        Command command = AjaxCommandProvider.getInstance().getCommand(commandName);

        command.execute(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
