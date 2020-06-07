package by.epam.tr.service.validation;

import by.epam.tr.service.validation.impl.*;

public class ValidationFactory {

    private static final ValidationFactory instance = new ValidationFactory();

    private final Validator userValidation = new UserValidation();
    private final Validator dateValidation = new DateValidation();
    private final Validator loginValidation = new LoginValidation();
    private final Validator passwordValidation = new PasswordValidation();
    private final Validator typeValidation = new FlightTypeValidation();

    private ValidationFactory(){}

    public static ValidationFactory getInstance(){
        return instance;
    }

    public Validator getUserValidation(){
        return userValidation;
    }

    public Validator getDateValidation() {
        return dateValidation;
    }

    public Validator getLoginValidation() {
        return loginValidation;
    }

    public Validator getPasswordValidation() {
        return passwordValidation;
    }

    public Validator getFlightTypeValidation() {
        return typeValidation;
    }
}
