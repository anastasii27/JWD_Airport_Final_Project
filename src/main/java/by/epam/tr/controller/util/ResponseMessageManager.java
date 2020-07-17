package by.epam.tr.controller.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResponseMessageManager {
    private final static String FILE_PATH = "localization/local";
    private String language;
    private  ResourceBundle bundle;

    public ResponseMessageManager(String language) {
        this.language = language;
        bundle = ResourceBundle.getBundle(FILE_PATH, new Locale(this.language));
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
