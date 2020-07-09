package by.epam.tr.controller.util;

import by.epam.tr.bean.User;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import javax.servlet.ServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import static by.epam.tr.controller.util.RequestParametersExtractor.*;

public class RequestToMapParser {
    private final static String AIRPORT_SHORT_NAME  = "airport_short_name";
    private static String key;
    private static int count = 1;

    public static Map<String, String> toRequestParamsMap(ServletRequest request){
        Map<String, String> params =  new HashMap<>();
        Enumeration<java.lang.String> keys = request.getParameterNames();

        while (keys.hasMoreElements()){
            key = keys.nextElement();
            params.put(key, request.getParameter(key));
        }
        return params;
    }

    public static Map<String, String> toFlightValidationParamsMap(ServletRequest request){
        Map<String, String> params =  new HashMap<>();
        Enumeration<String> keys = request.getParameterNames();

        while (keys.hasMoreElements()){
            key = keys.nextElement();
            if(key.equals(RequestParameterName.CITY)){
                params.put(AIRPORT_SHORT_NAME, airportName(request.getParameter(RequestParameterName.CITY)));
            }

            params.put(key, request.getParameter(key));
        }
        return params;
    }

    public static Map<String, String> toCrewValidationMap(ServletRequest request){
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
        return params;
    }

    private static void putMemberToMap(String [] crewMembers, Map<String, String> users){
        for (String member: crewMembers) {
            if (member.length()!= 0) {
                users.put(RequestParameterName.USER+count++, member);
            }
        }
    }

    public static Map<String, User> toCrewMembersMap(String [] stewards, String commander, String [] pilots){
        Map<String, User> users = new HashMap<>();
        User firstPilot;

        putUserToMap(stewards, users);
        putUserToMap(pilots, users);

        firstPilot = User.builder().name(userName(commander)).surname(userSurname(commander)).build();
        users.put(RequestParameterName.PILOT1, firstPilot);

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
}
