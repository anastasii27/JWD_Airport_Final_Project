package by.epam.tr.controller;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.command.CommandProvider;
import by.epam.tr.dao.connectionpool.ConnectionPool;
import by.epam.tr.dao.connectionpool.ConnectionPoolException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/Controller")
public class Controller extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher requestDispatcher;

        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");

        HttpSession session = req.getSession();
        User user =  (User) session.getAttribute("current_user");
        String[] commandName = req.getParameterValues("log");
        Command command = CommandProvider.getInstance().getCommand(commandName[0]);

        if(user == null) {

            user = new User(req.getParameter("login"),req.getParameter("password"));
            requestDispatcher = req.getRequestDispatcher(command.execute(req));
            session.setAttribute("current_user", user);

        }else {
            requestDispatcher = req.getRequestDispatcher(JSPPageName.HOME_PAGE);
        }

        if(requestDispatcher!= null){
            requestDispatcher.forward(req, resp);
        }else {
            errorMessage(resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void errorMessage(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("ERROR!");
    }

}
