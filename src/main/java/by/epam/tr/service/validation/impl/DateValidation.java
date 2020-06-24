package by.epam.tr.service.validation.impl;

import by.epam.tr.service.validation.ValidationPattern;
import by.epam.tr.service.validation.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DateValidation extends Validator {

    private final static int MIN_DAY = 10;
    private final static int MAX_DAY = 30;

    @Override
    public List<String> validate(Map<String, String> params) {

        List <String> result = new ArrayList<>();

        if(!emptyValueCheck(params)){
            result.add("You didnt` enter all values!");
            return result;
        }

        String departureDate = params.get("departure_date");
        if (!checkWithPattern(ValidationPattern.DATE_PATTERN,  departureDate)) {
            result.add("Illegal date!");
            return result;
        }

        if (!isDateRangeValid(departureDate)) {
            result.add("Illegal date range!");
        }
        return result;
    }

    private boolean isDateRangeValid(String date){

        LocalDate enteredDate = LocalDate.parse(date);
        LocalDate today = LocalDate.now();
        LocalDate minDateOfRange = today.minusDays(MIN_DAY);
        LocalDate maxDateOfRange = today.plusDays(MAX_DAY);

        if(enteredDate.isAfter(maxDateOfRange)){
            return false;
        }

        if(enteredDate.isBefore(minDateOfRange)){
            return false;
        }

        return true;
    }
}
