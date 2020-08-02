package by.epam.airport_system.service.validation;

import java.util.*;

public class ValidationResult {
    private final static String FILE_PATH = "localization/local";
    private final static String LOCAL_PARAM = "local";
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

    public void addError(String key){
        results.add(bundle.getString(key));
    }

    public boolean isEmpty(){
        return results.isEmpty();
    }

    public List<String> getErrorsList(){
        return results;
    }

    public static ValidationResult getValidationResult(Map<String,String> params){
        String lang = params.get(LOCAL_PARAM);

        if(lang == null){
            return new ValidationResult();
        }else {
            return new ValidationResult(lang);
        }
    }
}
