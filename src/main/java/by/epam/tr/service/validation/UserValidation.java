package by.epam.tr.service.validation;

import by.epam.tr.bean.User;

public abstract class UserValidation{

    private UserValidation next;

    private static final String TEXT_PATTERN ="[а-яА-Яa-zA-Z]+";
    private static final String NUMBER_PATTERN ="\\d+";
    private static final String NO_SIGN_PATTERN ="\\w+";
    private static final String EMAIL_PATTERN ="[\\w+[-.]*\\w+]+@[a-z]+.[a-z]{2,3}";

    public UserValidation linkWith(UserValidation next) {
        this.next = next;
        return next;
    }

    public abstract boolean check(User user, String login, String password);

    public boolean checkNext(User user, String email, String password) {
        if (next == null) {
            return true;
        }
        return next.check(user, email, password);
    }

    public static boolean textCheck(String value) {

        if(value.trim().matches(TEXT_PATTERN)){
            return true;
        }
        return false;
    }


    public boolean numberCheck(String value) {

        if(value.trim().matches(NUMBER_PATTERN)){
            return true;
        }
        return false;
    }


    public boolean noSignCheck(String value) {

        if(value.trim().matches(NO_SIGN_PATTERN)){
            return true;
        }
        return false;
    }

    public boolean emailCheck(String value) {

        if(value.trim().matches(EMAIL_PATTERN)){
            return true;
        }
        return false;
    }

    public boolean isNullCheck(String value) {

        if(value == null){
            return true;
        }
        return false;
    }

}

