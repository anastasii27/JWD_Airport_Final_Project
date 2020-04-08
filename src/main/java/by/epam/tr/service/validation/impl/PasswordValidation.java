package by.epam.tr.service.validation.impl;

import by.epam.tr.bean.User;
import by.epam.tr.service.validation.UserValidation;

public class PasswordValidation extends UserValidation{

    @Override
    public boolean check(User user, String login, String password) {

        if(!noSignCheck(password)){
            return false;
        }

        return checkNext(user, login, password);
    }
}
