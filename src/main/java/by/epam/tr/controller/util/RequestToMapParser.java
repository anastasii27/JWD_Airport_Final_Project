package by.epam.tr.controller.util;

import by.epam.tr.bean.User;
import by.epam.tr.controller.constant_parameter.RequestParameterName;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static by.epam.tr.controller.util.RequestParametersExtractor.*;

public class RequestToMapParser {

    private final static String AIRPORT_SHORT_NAME  = "airport_short_name";
    //todo переделать название методов
    private static String key;

    public static Map<String, String> requestParamsMap(HttpServletRequest request){

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

            if(key.equals(RequestParameterName.CITY)){
                params.put(AIRPORT_SHORT_NAME, getAirportName(request.getParameter(RequestParameterName.CITY)));
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

            if(key.equals(RequestParameterName.STEWARD)){
                putStewardsToMap(request.getParameterValues(key), params);
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

        if (stewards != null) {
            for (String s : stewards) {
                if (s.length() != 0) {
                    user = new User(userName(s), userSurname(s));
                    users.put(RequestParameterName.STEWARD+count++,user);
                }
            }
        }
        commander = new User(userName(firstPilot), userSurname(firstPilot));
        pilotNumberTwo = new User(userName(secondPilot), userSurname(secondPilot));

        users.put(RequestParameterName.PILOT1, commander);
        users.put(RequestParameterName.PILOT2, pilotNumberTwo);

        return users;
    }

    public static Map<String, String> myFlightMap(String departureDate, String surname, String email){

        Map<String, String> params =  new HashMap<>();

        params.put(RequestParameterName.DEPARTURE_DATE, departureDate);
        params.put(RequestParameterName.SURNAME, surname);
        params.put(RequestParameterName.EMAIL, email);

        return params;
    }

    private static void putStewardsToMap(String [] stewards, Map<String, String> params){
        int count=1;

        for (String s : stewards) {
            if (s.length()!= 0) {
                params.put(RequestParameterName.STEWARD+count++, s);
            }
        }
    }

    private static String getAirportName(String city){

        if (city == null) {
            return "";
        }
        int indexOfFirstBracket = city.indexOf('(')+1;
        int indexOfLastBracket = city.indexOf(')');

        return city.substring(indexOfFirstBracket, indexOfLastBracket);
    }
}
