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
}
