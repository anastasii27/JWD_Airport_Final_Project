package by.epam.tr.service.validation.impl;

import by.epam.tr.bean.User;
import by.epam.tr.service.validation.UserValidation;

public class UserInfoValidation extends UserValidation{

    @Override
    public boolean check(User user, String login, String password) {

        String name = user.getName();
        String surname = user.getSurname();
        String careerStartYear = user.getCareerStartYear();
        String email = user.getEmail();

        if(!emailCheck(email)){
            return false;
        }

        if(! textCheck(name) || ! textCheck(surname)){
            return false;
        }

        if(! numberCheck(careerStartYear)){
            return false;
        }

        return checkNext(user, login, password);

    }
}
