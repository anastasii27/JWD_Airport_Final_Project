package by.epam.tr.service.validation;

import by.epam.tr.service.validation.impl.*;

public class ValidationFactory {
    private static final ValidationFactory instance = new ValidationFactory();
    private final Validator dateValidation = new DateValidation();
    private final Validator registrationValidation = new RegistrationValidation();
    private final Validator crewValidation = new CrewValidation();
    private final Validator flightValidation = new FlightValidation();

    private ValidationFactory(){}

    public static ValidationFactory getInstance(){
        return instance;
    }

    public Validator getDateValidation() {
        return dateValidation;
    }

    public Validator getRegistrationValidation() {
        return registrationValidation;
    }

    public Validator getCrewValidation() {
        return crewValidation;
    }

    public Validator getFlightValidation() {
        return flightValidation;
    }
}
