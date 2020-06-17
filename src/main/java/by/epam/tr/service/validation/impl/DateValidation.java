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
        LocalDate date;

        if(!emptyValueCheck(params)){
            result.add("You didnt` enter all values!");
            return result;
        }

        if (!checkWithPattern(ValidationPattern.DATE_PATTERN,  params.get("departure_date"))) {
            result.add("Illegal date!");
            return result;
        }

        date = LocalDate.parse(params.get("departure_date"));

        if (!dateRangeCheck(date)) {
            result.add("Illegal date range!");
        }

        return result;
    }

    private boolean dateRangeCheck(LocalDate date){

        LocalDate today = LocalDate.now();
        LocalDate minDateOfRange = today.minusDays(MIN_DAY);
        LocalDate maxDateOfRange = today.plusDays(MAX_DAY);

        if(date.isAfter(maxDateOfRange)){
            return false;
        }

        if(date.isBefore(minDateOfRange)){
            return false;
        }

        return true;
    }
}
