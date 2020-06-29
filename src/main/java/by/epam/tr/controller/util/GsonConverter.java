package by.epam.tr.controller.util;

import com.google.gson.Gson;
import java.util.List;

public class GsonConverter {

    public static String convertListToGSON(List list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
