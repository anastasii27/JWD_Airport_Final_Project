package by.epam.tr.service.validation.impl;

import by.epam.tr.service.validation.Validator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RegistrationValidation extends Validator {

    @Override
    public List<String> validate(Map<String, String> params) {

        return Collections.emptyList();
    }
}
