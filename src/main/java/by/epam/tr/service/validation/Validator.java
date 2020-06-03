package by.epam.tr.service.validation;

import java.time.LocalDate;

public abstract class Validator {

    public abstract boolean validate(Object object);

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

    public static boolean dateRangeCheck(int minusFromToday, int plusToToday, LocalDate date){

        LocalDate today = LocalDate.now();
        LocalDate minDateOfRange = today.minusDays(minusFromToday);
        LocalDate maxDateOfRange = today.plusDays(plusToToday);

        if(date.isAfter(maxDateOfRange)){
            return false;
        }

        if(date.isBefore(minDateOfRange)){
            return false;
        }

        return true;
    }
}

