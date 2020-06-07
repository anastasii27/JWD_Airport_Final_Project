package by.epam.tr.service.validation.impl;

import by.epam.tr.service.validation.FlightType;
import by.epam.tr.service.validation.Validator;

public class FlightTypeValidation extends Validator {

    @Override
    public boolean validate(Object object) {

        String flightType = (String) object;

        if(!(nullCheck(flightType))){
            return false;
        }

        if(!doesFlightTypeExist(flightType)){
            return false;
        }
        return true;
    }

    private boolean doesFlightTypeExist(String flightType){

        boolean flag;

        try {
            FlightType.valueOf(flightType.toUpperCase());
            flag = true;

        }catch (IllegalArgumentException e){
            flag = false;
        }

        return flag;
    }
}
