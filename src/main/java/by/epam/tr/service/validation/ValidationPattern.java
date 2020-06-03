package by.epam.tr.service.validation;

public final class ValidationPattern {

    private ValidationPattern(){}

    public static final String TEXT_PATTERN ="[а-яА-Яa-zA-Z]+";
    public static final String NUMBER_PATTERN ="\\d+";
    public static final String NO_SIGN_PATTERN ="\\w+";
    public static final String EMAIL_PATTERN ="[\\w+[-.]*\\w+]+@[a-z]+[.][a-z]{2,3}";

}
