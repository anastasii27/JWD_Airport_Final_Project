package by.epam.tr.service.validation;

import by.epam.tr.service.validation.impl.LoginValidation;
import by.epam.tr.service.validation.impl.NotNullValidation;
import by.epam.tr.service.validation.impl.PasswordValidation;
import by.epam.tr.service.validation.impl.UserInfoValidation;

public class ValidationFactory {

    private static final ValidationFactory instance = new ValidationFactory();
    private UserValidation vp = new NotNullValidation();

    private ValidationFactory(){
        vp.linkWith(new PasswordValidation()).linkWith(new LoginValidation()).linkWith(new UserInfoValidation());
    }

    public static ValidationFactory getInstance(){
        return instance;
    }

    public UserValidation getUserValidation(){
        return vp;
    }
}
