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

        List <String> results = new ArrayList<>();

        if (!checkWithPattern(ValidationPattern.DATE_PATTERN,  params.get("departureDate"))) {
            results.add("Illegal date!");
            return results;
        }

        LocalDate date = LocalDate.parse(params.get("departureDate"));

        if (!dateRangeCheck(MIN_DAY, MAX_DAY, date)) {
            results.add("Illegal date range!");
        }

        return results;
    }

    private boolean dateRangeCheck(int minusFromToday, int plusToToday, LocalDate date){

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
