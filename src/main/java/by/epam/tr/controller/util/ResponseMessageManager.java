package by.epam.tr.controller.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResponseMessageManager {
    private final static String FILE_PATH = "localization/local";
    private String language = "ru";

    public ResponseMessageManager(String language) {
        if(language != null){
            this.language = language;
        }
    }

    public String getValue(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle(FILE_PATH, new Locale(language));
        return bundle.getString(key);
    }
}
