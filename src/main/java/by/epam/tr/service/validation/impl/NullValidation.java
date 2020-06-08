package by.epam.tr.service.validation.impl;

import by.epam.tr.service.validation.Validator;

public class NullValidation extends Validator {

    @Override
    public boolean validate(Object...object) {

        for (Object obj : object) {

            if(!nullCheck(obj)){
                return false;
            }
        }

        return true;
    }
}
