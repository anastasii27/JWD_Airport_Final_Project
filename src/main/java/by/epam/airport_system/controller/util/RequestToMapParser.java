package by.epam.airport_system.controller.util;

import by.epam.airport_system.bean.User;
import by.epam.airport_system.controller.constant_parameter.RequestParameterName;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import static by.epam.airport_system.controller.util.RequestParametersExtractor.*;

public class RequestToMapParser {
    private static String key;
    private static int count = 1;

    public static Map<String, String> toRequestParamsMap(ServletRequest request){
        Map<String, String> params =  new HashMap<>();
        Enumeration<java.lang.String> keys = request.getParameterNames();

        while (keys.hasMoreElements()){
            key = keys.nextElement();
            params.put(key, request.getParameter(key));
        }
        addLanguageValueToMap(request, params);

        return params;
    }


    public static Map<String, String> toCrewMap(ServletRequest request){
        Map<String, String> params =  new HashMap<>();
        Enumeration<String> keys = request.getParameterNames();

        while (keys.hasMoreElements()){
            key = keys.nextElement();
            if(key.equals(RequestParameterName.STEWARD)){
               putMemberToMap(request.getParameterValues(key), params);
            }

            if(key.equals(RequestParameterName.PILOT)){
                putMemberToMap(request.getParameterValues(key), params);
            }

            if(key.equals(RequestParameterName.PILOT1) || key.equals(RequestParameterName.CREW_NAME)){
                params.put(key, request.getParameter(key));
            }
        }
        addLanguageValueToMap(request, params);

        return params;
    }

    private static void putMemberToMap(String [] crewMembers, Map<String, String> users){
        for (String member: crewMembers) {
            if (member.length()!= 0) {
                users.put(RequestParameterName.USER+count++, member);
            }
        }
    }

    public static Map<String, User> toCrewMembersMap(ServletRequest request){
        Map<String, User> users = new HashMap<>();
        String commander = request.getParameter(RequestParameterName.PILOT1);
        String [] pilots = request.getParameterValues(RequestParameterName.PILOT);
        String [] stewards =  request.getParameterValues(RequestParameterName.STEWARD);

        User firstPilot = User.builder().name(userName(commander)).surname(userSurname(commander)).build();
        users.put(RequestParameterName.PILOT1, firstPilot);
        putUserToMap(stewards, users);
        putUserToMap(pilots, users);

        return users;
    }

    private static void putUserToMap(String [] crewMembers,  Map<String, User> users){
        User user;

        for (String member: crewMembers) {
            if (member.length() != 0) {
                user = User.builder().name(userName(member)).surname(userSurname(member)).build();
                users.put(RequestParameterName.USER+count++,user);
            }
        }
    }

    private static void addLanguageValueToMap(ServletRequest request, Map<String, String> params){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String lang = (String) httpServletRequest.getSession().getAttribute(RequestParameterName.LOCAL);

        params.put(RequestParameterName.LOCAL, lang);
    }
}
