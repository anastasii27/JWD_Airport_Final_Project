package by.epam.airport_system.controller.util;

import by.epam.airport_system.bean.User;
import java.util.ArrayList;
import java.util.List;

public class RequestParametersExtractor{

    public static String userName(String fullUserName){
        if(fullUserName == null || fullUserName.isEmpty()){
            return " ";
        }
        return fullUserName.substring(0, fullUserName.indexOf(' ')).trim();
    }

    public static String userSurname(String fullUserName){
        if(fullUserName == null || fullUserName.isEmpty()){
            return " ";
        }
        return fullUserName.substring(fullUserName.indexOf(' ')).trim();
    }

    public static List<User> usersListFromFullNamesString(String users){//todo переделать
        List<User> usersList = new ArrayList<>();
        if(users != null && users.length() != 0){
            String [] usersStringArray = users.split(" ");

            for(int i=0; i<usersStringArray.length; i=i+2){
                usersList.add(User.builder().name(usersStringArray[i]).surname(usersStringArray[i+1]).build());
            }
        }
        return usersList;
    }

    public static String airportName(String city){
        if (city == null) {
            return "";
        }
        int indexOfFirstBracket = city.indexOf('(')+1;
        int indexOfLastBracket = city.indexOf(')');

        return city.substring(indexOfFirstBracket, indexOfLastBracket);
    }

    public static String planeNumber(String plane){
        if (plane == null) {
            return "";
        }
        int firstIndexOfFlightNumber = plane.indexOf(" ", plane.indexOf(" ")+1)+1;

        return plane.substring(firstIndexOfFlightNumber);
    }
}

