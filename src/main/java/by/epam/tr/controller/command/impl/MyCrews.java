package by.epam.tr.controller.command.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyCrews implements Command {

    private static final String ANSWER = "Sorry, no crews were found";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        CrewService groupService = ServiceFactory.getInstance().getCrewService();
        User user  = (User) request.getSession().getAttribute("user");
        String crewName;
        String surname;
        String email;

        surname = user.getSurname();
        email = user.getEmail();

        try {
            crewName = groupService.userCrewForNearestFlight(surname, email);

            if(crewName == null){
                request.setAttribute(RequestParameterName.RESULT_INFO, ANSWER);
            }else {
                request.setAttribute(RequestParameterName.CREW, crewName);
            }

            forwardTo(request,response, JSPPageName.MY_CREWS_PAGE);

        } catch (ServiceException e) {
            errorPage(response);
        }
    }
}
