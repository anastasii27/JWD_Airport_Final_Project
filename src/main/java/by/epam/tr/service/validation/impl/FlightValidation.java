package by.epam.tr.service.validation.impl;

import by.epam.tr.dao.DaoException;
import by.epam.tr.dao.DaoFactory;
import by.epam.tr.dao.FlightDao;
import by.epam.tr.service.validation.ValidationPattern;
import by.epam.tr.service.validation.Validator;
import lombok.extern.log4j.Log4j2;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
public class FlightValidation extends Validator {

    @Override
    public List<String> validate(Map<String, String> params) {
        List<String> validationResult= new ArrayList<>();

        params.remove("user");

        if(!emptyValueCheck(params)){
            validationResult.add("You didn`t enter some values");
            return validationResult;
        }

        String departureDate = params.get("departure_date");
        String departureTime = params.get("departure_time");
        String destinationDate = params.get("destination_date");
        String destinationTime = params.get("destination_time");

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

        if(params.get("departure_airport").equals(params.get("destination_airport"))){
            validationResult.add("Equal airports");
        }

        try {
            if(!flightNumberCheck(params.get("flight_number"))){
                validationResult.add("Illegal flight number");
            }
        } catch (DaoException e) {
            log.error("Exception during flight number checking", e);
        }
        return validationResult;
    }

    private boolean timeFormatCheck(String...timeArr){
        for (String time: timeArr) {
            if(!checkWithPattern(ValidationPattern.TIME_PATTERN, time)){
                return false;
            }
        }
        return true;
    }

    private static boolean dateFormatCheck(String...dateArr){
        for (String date: dateArr) {
            if(!checkWithPattern(ValidationPattern.DATE_PATTERN, date)){
                return false;
            }
        }
        return true;
    }

    private boolean dateRangeCheck(String...dateArr){
        LocalDate localDate;

        for (String date: dateArr) {
            localDate = LocalDate.parse(date);
            if(localDate.isBefore(LocalDate.now())){
                return false;
            }
        }
        return true;
    }

    private boolean flightNumberCheck(String flightNumber) throws DaoException {
        FlightDao flightDao = DaoFactory.getInstance().getFlightDAO();

        if(flightDao.doesFlightNumberExist(flightNumber)){
            return false;
        }

        if(!checkWithPattern(ValidationPattern.FLIGHT_NUMBER_PATTERN, flightNumber)){
            return false;
        }
        return true;
    }

    private boolean areDatesValid(String departureDate, String destinationDate, String departureTime, String destinationTime){
        LocalDateTime departure = uniteDateAndTime(departureDate, departureTime);
        LocalDateTime arrival = uniteDateAndTime(destinationDate, destinationTime);

        return departure.isBefore(arrival);
    }

    private LocalDateTime uniteDateAndTime(String date, String time){
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);

        return LocalDateTime.of(localDate, localTime);
    }
}
