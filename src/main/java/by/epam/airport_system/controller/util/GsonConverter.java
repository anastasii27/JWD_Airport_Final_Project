package by.epam.airport_system.controller.util;

import com.google.gson.Gson;

public class GsonConverter {

    public static String convertToGson(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
