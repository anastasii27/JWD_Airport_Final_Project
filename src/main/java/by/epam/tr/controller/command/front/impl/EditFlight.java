package by.epam.tr.controller.command.front.impl;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.util.RequestToMapParser;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class EditFlight implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {//todo add validation
        Map<String, String>  params = RequestToMapParser.toRequestParamsMap(request);
        System.out.println(params);
    }
}
