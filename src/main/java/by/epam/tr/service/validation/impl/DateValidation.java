package by.epam.tr.service.validation.impl;

import by.epam.tr.service.validation.Validator;
import java.time.LocalDate;

public class DateValidation extends Validator {

    private final static int MIN_DAY = 10;
    private final static int MAX_DAY = 30;

    @Override
    public boolean validate(Object object) {

        LocalDate date = (LocalDate) object;

        if (!nullCheck(date)) {
            return false;
        }

        if(!dateRangeCheck(MIN_DAY, MAX_DAY, date)){
            return false;
        }

        return true;
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
