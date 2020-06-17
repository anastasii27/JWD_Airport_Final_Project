package by.epam.tr.controller.util;

import by.epam.tr.bean.User;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestToMapParser {

    private static String key;

    public  static Map<String, String> registerInfoMap(HttpServletRequest request){

        Map<String, String> params =  new HashMap<>();
        Enumeration<java.lang.String> keys = request.getParameterNames();

        while (keys.hasMoreElements()){
            key = keys.nextElement();
            params.put(key, request.getParameter(key));
        }
        return params;
    }

    public static Map<String, String> flightMap(HttpServletRequest request){

        Map<String, String> params =  new HashMap<>();
        Enumeration<String> keys = request.getParameterNames();

        while (keys.hasMoreElements()){

            key = keys.nextElement();

            if(key.equals("city")){
                params.put("airport_short_name", getAirportName(request.getParameter("city")));
            }
            params.put(key, request.getParameter(key));
        }
        return params;
    }

    public static Map<String, String> crewMembersMapForValidation(HttpServletRequest request){

        Map<String, String> params =  new HashMap<>();
        Enumeration<String> keys = request.getParameterNames();

        while (keys.hasMoreElements()){

            key = keys.nextElement();

            if(key.equals("steward")){
                stewards(request.getParameterValues(key), params);
            }
            params.put(key, request.getParameter(key));
        }
        return params;
    }

    public static Map<String, User> crewMembersMap(String [] stewards, String firstPilot, String secondPilot){

        Map<String, User> users = new HashMap<>();
        User user;
        User commander;
        User pilotNumberTwo;
        int count=1;

        for (String s : stewards) {
            if (s.length() != 0) {
                user = new User(userName(s), userSurname(s));
                users.put(RequestParameterName.STEWARD+count++,user);
            }
        }

        commander = new User(userName(firstPilot), userSurname(firstPilot));
        pilotNumberTwo = new User(userName(secondPilot), userSurname(secondPilot));

        users.put(RequestParameterName.PILOT1, commander);
        users.put(RequestParameterName.PILOT2, pilotNumberTwo);

        return users;
    }

    private static void stewards(String [] stewards, Map<String, String> params){
        int count=1;

        for (String s : stewards) {
            if (s.length()!= 0) {
                params.put(RequestParameterName.STEWARD+count++, s);
            }
        }
    }

    private static String userName(String fullUserName){

        return fullUserName.substring(0, fullUserName.indexOf(' ')).trim();
    }

    private static String userSurname(String fullUserName){

        return fullUserName.substring(fullUserName.indexOf(' ')).trim();
    }

    private static String getAirportName(String city){

        int indexOfFirstBracket = city.indexOf('(')+1;
        int indexOfLastBracket = city.indexOf(')');

        return city.substring(indexOfFirstBracket, indexOfLastBracket);
    }
}
