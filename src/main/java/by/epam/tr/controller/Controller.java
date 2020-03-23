package by.epam.tr.controller;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.command.CommandProvider;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/mmm")
public class Controller extends HttpServlet {

    @Override
    public void init(){

        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            pool.poolInitialization();
        } catch (ConnectionPoolException e) {
           //
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");

        String commandName = req.getParameter("action");
        Command command = CommandProvider.getInstance().getCommand(commandName);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(command.execute(req));


        if(requestDispatcher!= null){
            requestDispatcher.forward(req, resp);
        }else {
            errorMessage(resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private void errorMessage(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("ERROR!");
    }

    @Override
    public void destroy() {

        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            pool.closeAllConnections();
        } catch (ConnectionPoolException e) {
            //
        }
    }
}
