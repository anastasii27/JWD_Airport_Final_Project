package by.epam.airport_system.service.validation;

import by.epam.airport_system.service.validation.impl.*;

public class ValidationFactory {
    private static final ValidationFactory instance = new ValidationFactory();
    private final Validator userValidation = new UserValidationImpl();
    private final Validator crewValidation = new CrewValidationImpl();
    private final Validator flightValidation = new FlightValidationImpl();

    private ValidationFactory(){}

    public static ValidationFactory getInstance(){
        return instance;
    }

    public Validator getUserValidation() {
        return userValidation;
    }

    public Validator getCrewValidation() {
        return crewValidation;
    }

    public Validator getFlightValidation() {
        return flightValidation;
    }
}
