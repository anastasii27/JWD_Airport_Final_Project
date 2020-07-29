package by.epam.airport_system.controller.command.front.impl;

import by.epam.airport_system.bean.Flight;
import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.constant_parameter.JSPPageName;
import by.epam.airport_system.controller.constant_parameter.RequestParameterName;
import by.epam.airport_system.controller.util.ResponseMessageManager;
import by.epam.airport_system.service.CrewMemberService;
import by.epam.airport_system.service.CrewService;
import by.epam.airport_system.service.ServiceException;
import by.epam.airport_system.service.ServiceFactory;
import com.google.common.collect.Multimap;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Log4j2
public class ChooseCrewForFlight implements Command {
    private final static String ANSWER = "local.message.choose_crews.1";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        CrewMemberService crewMemberService = ServiceFactory.getInstance().getCrewMemberService();
        Flight flight = (Flight)request.getSession().getAttribute(RequestParameterName.FLIGHT);

        try {
            Multimap<String, User> freeCrewsWithMembers = null;

            if(flight !=  null) {
                Set<String> freeCrews = crewService.findFreeCrewsForFlight(flight);
                freeCrewsWithMembers = crewMemberService.allMembersOfCrews(freeCrews);

                if(freeCrewsWithMembers.size() != 0 ) {
                    request.setAttribute(RequestParameterName.CREW, freeCrews);
                    request.setAttribute(RequestParameterName.CREW_MEMBERS, freeCrewsWithMembers.asMap());
                }
            }

            if(flight == null || freeCrewsWithMembers == null || freeCrewsWithMembers.size()== 0){
                String language = String.valueOf(request.getSession().getAttribute(RequestParameterName.LOCAL));
                ResponseMessageManager resourceManager = new ResponseMessageManager(language);

                request.setAttribute(RequestParameterName.RESULT_INFO, resourceManager.getValue(ANSWER));
            }

            forwardTo(request, response, JSPPageName.FREE_CREWS_FOR_FLIGHT);
        } catch (ServiceException e) {
            log.error("Cannot execute command for free crew searching", e);
        }
    }
}