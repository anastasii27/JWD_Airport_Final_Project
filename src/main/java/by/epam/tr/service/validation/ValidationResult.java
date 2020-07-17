package by.epam.tr.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ValidationResult {
    private final static String FILE_PATH = "localization/local";
    private final static String DEFAULT_LANGUAGE = "ru";
    private List<String> results = new ArrayList<>();
    private String language;
    private ResourceBundle bundle;

    public ValidationResult(){
        bundle = ResourceBundle.getBundle(FILE_PATH, new Locale(DEFAULT_LANGUAGE));
    }

    public ValidationResult(String language){
        this.language = language;
        bundle = ResourceBundle.getBundle(FILE_PATH, new Locale(this.language));
    }

    public void addMessage(String key){
        results.add(bundle.getString(key));
    }

    public List<String> getResultsList(){
        return results;
    }

    public boolean isEmpty(){
        return results.isEmpty();
    }
}
