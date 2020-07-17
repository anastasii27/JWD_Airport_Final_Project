package by.epam.tr.service.validation;

import java.util.Map;

public interface Validator {

    ValidationResult validate(Map<String, String> params);

    default boolean checkWithPattern(String pattern,String value) {
        return value.trim().matches(pattern);
    }

    default boolean lengthCheck(int minLen, int maxLen, String value){
        return value.length() >= minLen && value.length() <= maxLen;
    }

    default boolean emptyValueCheck(Map<String, String> params){
        for(Map.Entry<String, String> param : params.entrySet()){
            if(param.getValue()== null || param.getValue().length()==0){
                return false;
            }
        }
        return true;
    }
}

