package by.epam.tr.controller.util;

public class RequestParametersExtractor{

    public static String userName(String fullUserName){

        return fullUserName.substring(0, fullUserName.indexOf(' ')).trim();
    }

    public static String userSurname(String fullUserName){

        return fullUserName.substring(fullUserName.indexOf(' ')).trim();
    }
}
