package by.epam.tr.service.validation.impl;

import by.epam.tr.bean.User;
import by.epam.tr.service.validation.Validator;

public class UserValidation extends Validator {

    @Override
    public boolean check(User user, String login, String password) {

        if(user == null){
            return false;
        }

        if(!noSignCheck(password) || !noSignCheck(login)){
            return false;
        }

        if(!lenCheck(4,15,login) || !lenCheck(6,15, password)){
            return false;
        }

        if(!textCheck(user.getName())){
            return false;
        }

        if(!textCheck(user.getSurname())){
            return false;
        }

        if(!numberCheck(user.getCareerStartYear())){
            return false;
        }

        if(!emailCheck(user.getEmail())){
            return false;
        }

        if(!lenCheck(user.getRole())){
            return false;
        }

        return true;
    }
}
