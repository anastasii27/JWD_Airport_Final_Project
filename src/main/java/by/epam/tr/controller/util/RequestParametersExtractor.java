package by.epam.tr.controller.util;

import by.epam.tr.bean.User;
import java.util.ArrayList;
import java.util.List;

public class RequestParametersExtractor{

    public static String userName(String fullUserName){

        return fullUserName.substring(0, fullUserName.indexOf(' ')).trim();
    }

    public static String userSurname(String fullUserName){

        return fullUserName.substring(fullUserName.indexOf(' ')).trim();
    }

    public static List<User> userFullName(String users){
        List<User> usersList = new ArrayList<>();
        if(users.length()!=0){
            String [] usersStringArray = users.split(" ");

            for(int i=0;i<usersStringArray.length;i=i+2){
                usersList.add(new User(usersStringArray[i], usersStringArray[i+1]));
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
