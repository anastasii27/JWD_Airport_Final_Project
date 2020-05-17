package by.epam.tr.service.validation;

import by.epam.tr.service.validation.impl.PlaneValidation;
import by.epam.tr.service.validation.impl.UserValidation;

public class ValidationFactory {

    private static final ValidationFactory instance = new ValidationFactory();

    private final Validator userValidation = new UserValidation();
    private final Validator planeValidation = new PlaneValidation();

    private ValidationFactory(){}

    public static ValidationFactory getInstance(){
        return instance;
    }

    public Validator getUserValidation(){
        return userValidation;
    }

    public Validator getPlaneValidation(){
        return planeValidation;
    }
}
