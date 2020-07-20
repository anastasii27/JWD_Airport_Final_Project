package by.epam.airport_system.service.validation;

public final class ValidationPattern {

    private ValidationPattern(){}

    public static final String TEXT_PATTERN ="[а-яА-Яa-zA-Z]+";
    public static final String NUMBER_PATTERN ="\\d+";
    public static final String NO_SIGN_PATTERN ="\\w+";
    public static final String EMAIL_PATTERN ="[\\w+[-.]*\\w+]+@[a-z]+[.][a-z]{2,3}";
    public static final String DATE_PATTERN ="^(202[0-9])[-]([0]?[1-9]|[1][0-2])[-]([0]?[1-9]|[1|2][0-9]|[3][0|1])$";
    public static final String TIME_PATTERN ="^(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$";
    public static final String CREW_NAME_PATTERN ="[A-Z]{1}\\d{1,3}";
    public static final String FLIGHT_NUMBER_PATTERN ="[A-Z]+\\s\\d+";
}
