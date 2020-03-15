package by.epam.tr.controller;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.command.CommandProvider;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Controller")
public class Controller extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       

        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");

        String [] commandName = req.getParameterValues("log");
        Command command = CommandProvider.getInstance().getCommand(commandName[0]);
        RequestDispatcher requestDispatcher =req.getRequestDispatcher(command.execute(req));
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
