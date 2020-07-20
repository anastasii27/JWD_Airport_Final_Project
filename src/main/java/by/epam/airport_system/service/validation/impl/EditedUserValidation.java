package by.epam.airport_system.service.validation.impl;

import by.epam.airport_system.service.validation.UserValidator;
import by.epam.airport_system.service.validation.ValidationResult;
import java.util.Map;

public class EditedUserValidation extends UserValidator {

    @Override
    public ValidationResult validate(Map<String, String> params) {
        ValidationResult result = getValidationResult(params);

        if(!emptyValueCheck(params)){
            result.addError(KEY1);
            return result;
        }

        if(!checkName(params.get(USER_NAME_PARAM))){
            result.addError(KEY2);
        }

        if(!checkSurname(params.get(USER_SURNAME_PARAM))){
            result.addError(KEY3);
        }

        if(!checkEmail(params.get(USER_EMAIL_PARAM))){
            result.addError(KEY4);
        }

        if(!checkCareerStartYear(params.get(USER_CAREER_START_YEAR_PARAM))){
            result.addError(KEY5);
        }

        return result;
    }
}
