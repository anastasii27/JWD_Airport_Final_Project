package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.PageByRole;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserPage implements Command {

    private static final String ANSWER = "Sorry..Something went wrong";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        User user = (User)request.getSession().getAttribute(RequestParameterName.USER_INFO);
        PageByRole page = PageByRole.getInstance();

        if(user != null){
            forwardTo(request,response,page.getPage(user.getRole()));
        }else {
            request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            forwardTo(request,response,JSPPageName.RESULT_PAGE);
        }

    }
}
