package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCrewMember implements Command {

    private Logger logger = LogManager.getLogger(getClass());
    //todo продумать возврат ответа
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        String crewName;
        String member;
        User user;

        crewName = request.getParameter(RequestParameterName.CREW_NAME);
        member = request.getParameter(RequestParameterName.USER);
        user = new User(userName(member), userSurname(member));
        try {
            crewService.deleteCrewMember(crewName, user);

            response.getWriter().write("true");

        } catch (ServiceException e) {
            logger.error("Cannot execute ajax command for crew member deleting", e);
        } catch (IOException e) {
            logger.error("Cannot write json to response", e);
        }
    }

    //todo убрать отсюда
    private String userName(String fullUserName){

        return fullUserName.substring(0, fullUserName.indexOf(' ')).trim();
    }

    private String userSurname(String fullUserName){

        return fullUserName.substring(fullUserName.indexOf(' ')).trim();
    }

}
