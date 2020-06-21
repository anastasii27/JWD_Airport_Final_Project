package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.ListCreatorService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateCrewPage implements Command {

    private Logger logger = LogManager.getLogger(getClass());
    private static final String PILOT = "pilot";
    private static final String STEWARD = "steward";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        ListCreatorService listCreatorService = ServiceFactory.getInstance().getListCreatorService();
        List<User> pilots;
        List<User> stewards;
        try {

            pilots = listCreatorService.createUserByRoleList(PILOT);
            stewards = listCreatorService.createUserByRoleList(STEWARD);

            request.setAttribute(RequestParameterName.PILOTS, pilots);
            request.setAttribute(RequestParameterName.STEWARDS, stewards);

            forwardTo(request, response, JSPPageName.CREATE_CREW_PAGE);

        } catch (ServiceException e) {
            logger.error("Cannot execute command for creating crew page", e);
            errorPage(response);
        }
    }
}
