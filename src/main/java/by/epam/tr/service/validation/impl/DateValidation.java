package by.epam.tr.service.validation.impl;

import by.epam.tr.service.validation.ValidationPattern;
import by.epam.tr.service.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DateValidation extends Validator {//todo переделать
    private final static String DEPARTURE_DATE_PARAM = "departure_date";

    @Override
    public List<String> validate(Map<String, String> params) {
        List <String> validationResult = new ArrayList<>();

        if(!emptyValueCheck(params)){
            validationResult.add("You didnt` enter all values!");
            return validationResult;
        }

        if (!checkWithPattern(ValidationPattern.DATE_PATTERN,  params.get(DEPARTURE_DATE_PARAM))) {
            validationResult.add("Illegal date!");
            return validationResult;
        }

        return validationResult;
    }

}
