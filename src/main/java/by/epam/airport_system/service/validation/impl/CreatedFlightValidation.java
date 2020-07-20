package by.epam.airport_system.service.validation.impl;

import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.service.validation.FlightValidator;
import by.epam.airport_system.service.validation.ValidationResult;
import lombok.extern.log4j.Log4j2;
import java.util.Map;

@Log4j2
public class CreatedFlightValidation extends FlightValidator {

    @Override
    public ValidationResult validate(Map<String, String> params) {
        ValidationResult result = getValidationResult(params);

        params.remove(FLIGHT_USER_PARAM);

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

        if(params.get(FLIGHT_DEPARTURE_AIRPORT_PARAM).equals(params.get(FLIGHT_DESTINATION_AIRPORT_PARAM))){
            result.addError(KEY5);
        }

        try {
            if(!flightNumberCheck(params.get(FLIGHT_NUMBER_PARAM), departureDate)){
                result.addError(KEY6);
            }

            if(!flightNumberCheck(params.get(FLIGHT_NUMBER_PARAM), destinationDate)){
                result.addError(KEY6);
            }
        } catch (DaoException e) {
            log.error("Exception during flight number checking", e);
        }
        return result;
    }
}
