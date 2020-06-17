package by.epam.tr.service.validation;

import java.util.List;
import java.util.Map;

public abstract class Validator {

    public abstract List<String> validate(Map<String, String> params);

    public static boolean checkWithPattern(String pattern,String value) {

        return value.trim().matches(pattern);
    }

    public  boolean lengthCheck(int minLen, int maxLen, String value){

        return value.length() >= minLen && value.length() <= maxLen;
    }

    public  boolean emptyValueCheck(Map<String, String> params){

        for(Map.Entry<String, String> param : params.entrySet()){
            if(param.getValue().length()==0){
                return false;
            }
        }
        return true;
    }
}

