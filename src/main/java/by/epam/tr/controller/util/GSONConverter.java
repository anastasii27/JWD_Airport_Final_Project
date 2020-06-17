package by.epam.tr.controller.util;

import com.google.gson.Gson;
import java.util.List;

public class GSONConverter {

    private static Gson gson = new Gson();

    public static String convertListToGSON(List list){

        return gson.toJson(list);
    }
}
