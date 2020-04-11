package by.epam.tr.controller.command.impl;

import by.epam.tr.controller.command.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowRegisterPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        try{
           response.sendRedirect("http://localhost:8080/JWD_Task3_war/jsp/register.jsp");

        } catch ( IOException e) {
            errorPage(response);
        }
    }

}
