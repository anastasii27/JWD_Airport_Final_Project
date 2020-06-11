package by.epam.tr.controller.command.ajax.impl;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import by.epam.tr.service.CrewService;
import by.epam.tr.service.ServiceException;
import by.epam.tr.service.ServiceFactory;
import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CrewMembers implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        CrewService crewService = ServiceFactory.getInstance().getCrewService();
        List crewList;
        String crewName;
        String crewGson;

        crewName = request.getParameter(RequestParameterName.CREW_NAME);

        try {

            crewList = crewService.crewMembers(crewName);
            crewGson = convertListIntoGSON(crewList);

            response.getWriter().write(crewGson);

        } catch (ServiceException|IOException e) {
            errorPage(response);
        }
    }

    private String convertListIntoGSON(List list){

        Gson gson = new Gson();
        String gsonList = gson.toJson(list);

        return gsonList;
    }
}
