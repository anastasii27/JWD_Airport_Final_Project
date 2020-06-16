package by.epam.tr.service.validation;

import java.util.List;
import java.util.Map;

public abstract class Validator {

    public abstract List<String> validate(Map<String, String> params);

    public static boolean checkWithPattern(String pattern,String...value) {

        for (String s : value) {

            if (!s.trim().matches(pattern)) {
                return false;
            }
        }
        return true;
    }

    public  boolean lengthCheck(int minLen, int maxLen, String value){

        if(!(value.length()>= minLen && value.length()<=maxLen)){
            return false;
        }

        return true;
    }

    public boolean nullCheck(Object...objects){

       for(Object obj: objects){

           if(obj == null){
               return false;
           }
       }
        return true;
    }

}

