package by.epam.tr.service.validation.impl;

import by.epam.tr.service.validation.ValidationPattern;
import by.epam.tr.service.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DateValidation extends Validator {//todo переделать

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

        return result;
    }

}
