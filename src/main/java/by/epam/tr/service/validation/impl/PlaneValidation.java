package by.epam.tr.service.validation.impl;

import by.epam.tr.bean.User;
import by.epam.tr.service.validation.Validator;

public class PlaneValidation extends Validator {

    @Override
    public boolean check(User user, String login, String password) {
        return false;
    }
}
