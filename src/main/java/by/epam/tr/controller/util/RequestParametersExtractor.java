package by.epam.tr.controller.util;

import by.epam.tr.bean.User;
import java.util.ArrayList;
import java.util.List;

public class RequestParametersExtractor{

    public static String userName(String fullUserName){
        if(fullUserName == null){
            return " ";
        }
        return fullUserName.substring(0, fullUserName.indexOf(' ')).trim();
    }

    public static String userSurname(String fullUserName){
        if(fullUserName == null){
            return " ";
        }
        return fullUserName.substring(fullUserName.indexOf(' ')).trim();
    }

    public static List<User> userFullName(String users){//todo переделать
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
}

