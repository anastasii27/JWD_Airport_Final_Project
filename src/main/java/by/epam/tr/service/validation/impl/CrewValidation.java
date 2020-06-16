package by.epam.tr.service.validation.impl;

import by.epam.tr.service.validation.Validator;

import java.util.List;
import java.util.Map;

public class CrewValidation extends Validator {

    @Override
    public List<String> validate(Map<String, String> params) {

        System.out.println(params.get(1));
        return null;
    }
}
