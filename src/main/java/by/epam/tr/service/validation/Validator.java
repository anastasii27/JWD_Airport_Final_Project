package by.epam.tr.service.validation;

import by.epam.tr.bean.User;

public abstract class Validator{

    private static final String TEXT_PATTERN ="[а-яА-Яa-zA-Z]+";
    private static final String NUMBER_PATTERN ="\\d+";
    private static final String NO_SIGN_PATTERN ="\\w+";
    private static final String EMAIL_PATTERN ="[\\w+[-.]*\\w+]+@[a-z]+[.][a-z]{2,3}";

    public abstract boolean check(User user, String login, String password);

    public boolean textCheck(String value) {

        if(value==null){
            return false;
        }

        if(value.trim().matches(TEXT_PATTERN)){
            return true;
        }
        return false;
    }


    public boolean numberCheck(String value) {

        if(value==null){
            return false;
        }

        if(value.trim().matches(NUMBER_PATTERN)){
            return true;
        }
        return false;
    }


    public boolean noSignCheck(String value) {

        if(value==null){
            return false;
        }

        if(value.trim().matches(NO_SIGN_PATTERN)){
            return true;
        }
        return false;
    }

    public boolean emailCheck(String value) {

        if(value==null){
            return false;
        }

        if(value.trim().matches(EMAIL_PATTERN)){
            return true;
        }
        return false;
    }


    public boolean lenCheck(int beginning, int end, String value){

        if(value==null){
            return false;
        }

        if(value.length()>= beginning && value.length()<=end){
            return true;
        }

        return false;
    }

    public boolean lenCheck(String value){

        if(value==null){
            return false;
        }

        if(value.length() != 0){
            return true;
        }

        return false;
    }
}