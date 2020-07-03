package by.epam.tr.controller.command.front.impl;

import by.epam.tr.bean.Flight;
import by.epam.tr.bean.User;
import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.JSPPageName;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.CrewMemberService;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import com.google.common.collect.Multimap;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Log4j2
public class FreeCrewsForFlight implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        CrewMemberService crewMemberService = ServiceFactory.getInstance().getCrewMemberService();
        Flight flight = (Flight)request.getSession().getAttribute(RequestParameterName.FLIGHT);

        try {
            if(flight !=  null) {
                Set<String> freeCrews = crewService.findFreeCrewsForFlight(flight);
                Multimap<String, User> freeCrewsWithMembers = crewMemberService.allMembersOfCrews(freeCrews);

                request.setAttribute(RequestParameterName.CREW, freeCrews);
                request.setAttribute(RequestParameterName.CREW_MEMBERS, freeCrewsWithMembers.asMap());
                forwardTo(request, response, JSPPageName.FREE_CREWS_FOR_FLIGHT);
            }else {
                //
            }
        } catch (ServiceException e) {
            log.error("Cannot execute command for free flight searching", e);
        }
    }
}
