package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.PageByRole;
import by.epam.tr.controller.constant_parameter.RequestParameterName;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        //null check
        User user = (User)request.getSession().getAttribute(RequestParameterName.USER_INFO);
        PageByRole page = PageByRole.getInstance();

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(page.getPage(user.getRole()));

        try {
            if(requestDispatcher!= null){
                requestDispatcher.forward(request, response);
            }
        } catch (ServletException | IOException e) {
            errorPage(response);
        }
    }
}
