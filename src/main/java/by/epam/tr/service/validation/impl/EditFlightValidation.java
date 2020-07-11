package by.epam.tr.service.validation.impl;

import by.epam.tr.service.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditFlightValidation extends FlightValidation implements Validator {
    private final static String FLIGHT_DEPARTURE_DATE_PARAM = "departure_date";
    private final static String FLIGHT_DEPARTURE_TIME_PARAM = "departure_time";
    private final static String FLIGHT_DESTINATION_DATE_PARAM = "destination_date";
    private final static String FLIGHT_DESTINATION_TIME_PARAM = "destination_time";

    @Override
    public List<String> validate(Map<String, String> params) {
        List<String> validationResult= new ArrayList<>();

        params.remove("crews");

        if(!emptyValueCheck(params)){
            validationResult.add("You didn`t enter some values");
            return validationResult;
        }

        String departureDate = params.get(FLIGHT_DEPARTURE_DATE_PARAM);
        String departureTime = params.get(FLIGHT_DEPARTURE_TIME_PARAM);
        String destinationDate = params.get(FLIGHT_DESTINATION_DATE_PARAM);
        String destinationTime = params.get(FLIGHT_DESTINATION_TIME_PARAM);

        if(!timeFormatCheck(departureTime, destinationTime)){
            validationResult.add("Illegal time");
        }

        if(!dateFormatCheck(departureDate, destinationDate)){
            validationResult.add("Illegal date");
            return validationResult;
        }

        if(!dateRangeCheck(departureDate, destinationDate)){
            validationResult.add("Illegal date range");
        }

        if(!areDatesValid(departureDate, destinationDate, departureTime, destinationTime)){
            validationResult.add("Departure date is earlier than destination one");
        }

        return validationResult;
    }
}
