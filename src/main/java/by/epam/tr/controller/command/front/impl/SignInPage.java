package by.epam.tr.controller.command.front.impl;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInPage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

       forwardTo(request,response, JSPPageName.SIGN_IN_PAGE);
    }
}
