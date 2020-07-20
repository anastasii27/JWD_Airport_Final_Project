package by.epam.airport_system.service.validation;

import by.epam.airport_system.dao.DaoException;
import by.epam.airport_system.dao.DaoFactory;
import by.epam.airport_system.dao.FlightDao;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class FlightValidator extends Validator{
    public final static String FLIGHT_DEPARTURE_DATE_PARAM = "departure_date";
    public final static String FLIGHT_DEPARTURE_TIME_PARAM = "departure_time";
    public final static String FLIGHT_DEPARTURE_AIRPORT_PARAM = "departure_airport";
    public final static String FLIGHT_DESTINATION_DATE_PARAM = "destination_date";
    public final static String FLIGHT_DESTINATION_TIME_PARAM = "destination_time";
    public final static String FLIGHT_DESTINATION_AIRPORT_PARAM = "destination_airport";
    public final static String FLIGHT_USER_PARAM = "user";
    public final static String FLIGHT_NUMBER_PARAM = "flight_number";
    public final static String KEY1= "local.validation.edit.1";
    public final static String KEY2= "local.validation.edit.2";
    public final static String KEY3= "local.validation.edit.3";
    public final static String KEY4= "local.validation.edit.4";
    public final static String KEY5= "local.validation.flight.1";
    public final static String KEY6= "local.validation.flight.2";

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

    public boolean flightNumberCheck(String flightNumber, String date) throws DaoException {
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

    public LocalDateTime uniteDateAndTime(String date, String time){
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);

        return LocalDateTime.of(localDate, localTime);
    }
}
