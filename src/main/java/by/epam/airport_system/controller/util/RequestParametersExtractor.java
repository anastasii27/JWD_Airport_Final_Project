package by.epam.airport_system.controller.util;

import by.epam.airport_system.bean.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestParametersExtractor{
    private final static String EMPTY_STRING = " ";
    private final static String LEFT_BRACKET = "(";
    private final static String RIGHT_BRACKET = ")";
    private final static String REGEX = ".+\\s.+\\s.+";

    public static String extractName(String value){
        if(isParameterValid(value) && value.contains(EMPTY_STRING)){
            return value.substring(0, value.indexOf(EMPTY_STRING)).trim();
        }
        return EMPTY_STRING;
    }

    public static String extractSurname(String value){
        if(isParameterValid(value) && value.contains(EMPTY_STRING)) {
            return value.substring(value.indexOf(' ')).trim();
        }
        return EMPTY_STRING;
    }

    public static List<User> usersList(String fullNames){
        if(!isParameterValid(fullNames) || !fullNames.contains(EMPTY_STRING)){
            return Collections.emptyList();
        }

        String [] usersStringArray = fullNames.split(EMPTY_STRING);
        List<User> usersList = new ArrayList<>();

        for(int i=0; i<usersStringArray.length; i=i+2){
            if(i+1<usersStringArray.length) {
                usersList.add(User.builder().name(usersStringArray[i]).surname(usersStringArray[i + 1]).build());
            }
        }

        return usersList;
    }

    public static String extractAirportName(String value){
       if(!isParameterValid(value)) {
           return EMPTY_STRING;
       }

        int indexOfFirstBracket = value.indexOf(LEFT_BRACKET);
        int indexOfLastBracket = value.indexOf(RIGHT_BRACKET);

        if(indexOfFirstBracket != -1 && indexOfLastBracket != -1) {
            return value.substring(indexOfFirstBracket + 1, indexOfLastBracket);
        }
        return EMPTY_STRING;
    }

    public static String extractPlaneNumber(String value){
        if (!isParameterValid(value)) {
            return EMPTY_STRING;
        }

        if(value.matches(REGEX)){
            int firstIndexOfFlightNumber = value.indexOf(EMPTY_STRING, value.indexOf(EMPTY_STRING)+1);
            return value.substring(firstIndexOfFlightNumber+1);
        }

        return EMPTY_STRING;
    }

    private static boolean isParameterValid(String param){
        return param != null && !param.isEmpty();
    }
}

