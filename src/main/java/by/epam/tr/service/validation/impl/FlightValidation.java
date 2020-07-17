package by.epam.tr.service.validation.impl;

import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.DaoFactory;
import by.epam.tr.dao.FlightDao;
import by.epam.tr.service.validation.ValidationPattern;
import by.epam.tr.service.validation.ValidationResult;
import by.epam.tr.service.validation.Validator;
import lombok.extern.log4j.Log4j2;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@Log4j2
public class FlightValidation implements Validator {
    private final static String FLIGHT_DEPARTURE_DATE_PARAM = "departure_date";
    private final static String FLIGHT_DEPARTURE_TIME_PARAM = "departure_time";
    private final static String FLIGHT_DEPARTURE_AIRPORT_PARAM = "departure_airport";
    private final static String FLIGHT_DESTINATION_DATE_PARAM = "destination_date";
    private final static String FLIGHT_DESTINATION_TIME_PARAM = "destination_time";
    private final static String FLIGHT_DESTINATION_AIRPORT_PARAM = "destination_airport";
    private final static String FLIGHT_USER_PARAM = "user";
    private final static String FLIGHT_NUMBER_PARAM = "flight_number";
    private final static String LOCAL_PARAM = "local";
    private final static String KEY1= "local.validation.edit.1";
    private final static String KEY2= "local.validation.edit.2";
    private final static String KEY3= "local.validation.edit.3";
    private final static String KEY4= "local.validation.edit.4";
    private final static String KEY5= "local.validation.flight.1";
    private final static String KEY6= "local.validation.flight.2";

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

    public boolean timeFormatCheck(String...timeArr){
        for (String time: timeArr) {
            if(!checkWithPattern(ValidationPattern.TIME_PATTERN, time)){
                return false;
            }
        }
        return true;
    }

    public boolean dateFormatCheck(String...dateArr){
        for (String date: dateArr) {
            if(!checkWithPattern(ValidationPattern.DATE_PATTERN, date)){
                return false;
            }
        }
        return true;
    }

    public boolean dateRangeCheck(String...dateArr){
        LocalDate localDate;

        for (String date: dateArr) {
            localDate = LocalDate.parse(date);
            if(localDate.isBefore(LocalDate.now())){
                return false;
            }
        }
        return true;
    }

    private boolean flightNumberCheck(String flightNumber, String date) throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();

        if(flightDao.doesFlightNumberExist(flightNumber, LocalDate.parse(date))){
            return false;
        }

        if(!checkWithPattern(ValidationPattern.FLIGHT_NUMBER_PATTERN, flightNumber)){
            return false;
        }
        return true;
    }

    public boolean areDatesValid(String departureDate, String destinationDate, String departureTime, String destinationTime){
        LocalDateTime departure = uniteDateAndTime(departureDate, departureTime);
        LocalDateTime arrival = uniteDateAndTime(destinationDate, destinationTime);

        return departure.isBefore(arrival);
    }

    private LocalDateTime uniteDateAndTime(String date, String time){
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);

        return LocalDateTime.of(localDate, localTime);
    }

    private ValidationResult getValidationResult(Map<String,String> params){//todo вынести
        String lang = params.get(LOCAL_PARAM);
        ValidationResult result;

        if(lang == null){
            result = new ValidationResult();
        }else {
            result = new ValidationResult(lang);
        }
        return result;
    }
}
