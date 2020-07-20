package by.epam.airport_system.service.validation;

import java.util.Map;

public abstract class Validator {
    public abstract ValidationResult validate(Map<String, String> params);
    private final static String LOCAL_PARAM = "local";

    public boolean checkWithPattern(String pattern,String value) {
        return value.trim().matches(pattern);
    }

    public boolean lengthCheck(int minLen, int maxLen, String value){
        return value.length() >= minLen && value.length() <= maxLen;
    }

    public boolean emptyValueCheck(Map<String, String> params){
        for(Map.Entry<String, String> param : params.entrySet()){
            if(param.getValue()== null || param.getValue().length()==0){
                return false;
            }
        }
        return true;
    }

    public ValidationResult getValidationResult(Map<String,String> params){
        String lang = params.get(LOCAL_PARAM);

        if(lang == null){
            return new ValidationResult();
        }else {
            return new ValidationResult(lang);
        }
    }
}

