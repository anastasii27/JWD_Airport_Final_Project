package by.epam.tr.service.validation.impl;

import by.epam.tr.bean.User;
import by.epam.tr.service.validation.ValidationPattern;
import by.epam.tr.service.validation.Validator;

public class UserValidation extends Validator {

    @Override
    public boolean validate(Object...object) {

        User user;

        for (Object obj : object) {

            user = (User) obj;

            if (user == null) {
                return false;
            }

            String name = user.getName();
            String surname = user.getSurname();
            String email = user.getEmail();
            String careerStartYear = user.getCareerStartYear();

            if (!nullCheck(name, surname, email, careerStartYear)) {
                return false;
            }

            if (!checkWithPattern(ValidationPattern.TEXT_PATTERN, name, surname)) {
                return false;
            }

            if (!checkWithPattern(ValidationPattern.EMAIL_PATTERN, email)) {
                return false;
            }

            if (!checkWithPattern(ValidationPattern.NUMBER_PATTERN, careerStartYear)) {
                return false;
            }
        }
        return true;
    }
}
