package by.epam.airport_system.service.validation;

import by.epam.airport_system.service.validation.impl.*;

public class ValidationFactory {
    private static final ValidationFactory instance = new ValidationFactory();
    private final Validator createdUserValidation = new CreatedUserValidation();
    private final Validator editedUserValidation = new EditedUserValidation();
    private final Validator crewValidation = new CrewValidation();
    private final Validator createdFlightValidation = new CreatedFlightValidation();
    private final Validator editFlightValidation = new EditedFlightValidation();

    private ValidationFactory(){}

    public static ValidationFactory getInstance(){
        return instance;
    }

    public Validator getCreatedUserValidation() {
        return createdUserValidation;
    }

    public Validator getEditedUserValidation() {
        return editedUserValidation;
    }

    public Validator getCrewValidation() {
        return crewValidation;
    }

    public Validator getCreatedFlightValidation() {
        return createdFlightValidation;
    }

    public Validator getEditFlightValidation() {
        return editFlightValidation;
    }
}
