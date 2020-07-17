package by.epam.tr.service.validation.impl;

import by.epam.tr.service.validation.ValidationResult;
import by.epam.tr.service.validation.Validator;
import java.util.Map;

public class EditFlightValidation extends FlightValidation implements Validator {
    private final static String FLIGHT_DEPARTURE_DATE_PARAM = "departure_date";
    private final static String FLIGHT_DEPARTURE_TIME_PARAM = "departure_time";
    private final static String FLIGHT_DESTINATION_DATE_PARAM = "destination_date";
    private final static String FLIGHT_DESTINATION_TIME_PARAM = "destination_time";
    private final static String KEY1= "local.validation.edit.1";
    private final static String KEY2= "local.validation.edit.2";
    private final static String KEY3= "local.validation.edit.3";
    private final static String KEY4= "local.validation.edit.4";

    @Override
    public  ValidationResult validate(Map<String, String> params) {
        ValidationResult result = new ValidationResult();

        params.remove("crews");

        if(!emptyValueCheck(params)){
            result.addMessage(KEY1);
            return result;
        }

        String departureDate = params.get(FLIGHT_DEPARTURE_DATE_PARAM);
        String departureTime = params.get(FLIGHT_DEPARTURE_TIME_PARAM);
        String destinationDate = params.get(FLIGHT_DESTINATION_DATE_PARAM);
        String destinationTime = params.get(FLIGHT_DESTINATION_TIME_PARAM);

        if(!timeFormatCheck(departureTime, destinationTime)){
            result.addMessage(KEY2);
        }

        if(!dateFormatCheck(departureDate, destinationDate)){
            result.addMessage(KEY3);
            return result;
        }

        if(!dateRangeCheck(departureDate, destinationDate)){
            result.addMessage(KEY3);
        }

        if(!areDatesValid(departureDate, destinationDate, departureTime, destinationTime)){
            result.addMessage(KEY4);
        }
        return result;
    }
}
