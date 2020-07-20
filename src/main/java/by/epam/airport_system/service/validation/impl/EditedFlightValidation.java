package by.epam.airport_system.service.validation.impl;

import by.epam.airport_system.service.validation.FlightValidator;
import by.epam.airport_system.service.validation.ValidationResult;
import java.util.Map;

public class EditedFlightValidation extends FlightValidator {
    private final static String KEY = "crews";

    @Override
    public  ValidationResult validate(Map<String, String> params) {
        ValidationResult result = getValidationResult(params);

        params.remove(KEY);

        if(!emptyValueCheck(params)){
            result.addError(KEY1);
            return result;
        }

        String departureDate = params.get(FLIGHT_DEPARTURE_DATE_PARAM);
        String departureTime = params.get(FLIGHT_DEPARTURE_TIME_PARAM);
        String destinationDate = params.get(FLIGHT_DESTINATION_DATE_PARAM);
        String destinationTime = params.get(FLIGHT_DESTINATION_TIME_PARAM);

        if(!timeFormatCheck(departureTime, destinationTime)){
            result.addError(KEY2);
        }

        if(!dateFormatCheck(departureDate, destinationDate)){
            result.addError(KEY3);
            return result;
        }

        if(!dateRangeCheck(departureDate, destinationDate)){
            result.addError(KEY3);
        }

        if(!areDatesValid(departureDate, destinationDate, departureTime, destinationTime)){
            result.addError(KEY4);
        }
        return result;
    }
}
