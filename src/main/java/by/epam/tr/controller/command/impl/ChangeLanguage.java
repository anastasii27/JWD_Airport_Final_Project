package by.epam.tr.controller.command.impl;

import by.epam.tr.controller.command.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLanguage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            //String url = request.getSession().getAttribute("url").toString();

            String url = request.getParameter("url");

            System.out.println(url);

            request.getSession(true).setAttribute("local", request.getParameter("local"));
            response.sendRedirect(url);

        } catch (IOException e) {
            errorPage(response);
        }
    }
}
