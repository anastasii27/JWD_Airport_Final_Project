package by.epam.airport_system.service.validation;

public abstract class UserValidator extends Validator {
    public final static String USER_NAME_PARAM = "user_name";
    public final static String USER_SURNAME_PARAM = "surname";
    public final static String USER_EMAIL_PARAM = "email";
    public final static String USER_CAREER_START_YEAR_PARAM = "career_start_year";
    public final static String KEY1= "local.validation.edit.1";
    public final static String KEY2= "local.validation.user.1";
    public final static String KEY3= "local.validation.user.2";
    public final static String KEY4= "local.validation.user.3";
    public final static String KEY5= "local.validation.user.4";
    
    public boolean checkName(String name){
        return checkWithPattern(ValidationPattern.TEXT_PATTERN, name);
    }

    public boolean checkSurname(String surname){
        return checkWithPattern(ValidationPattern.TEXT_PATTERN, surname);
    }

    public boolean checkEmail(String email){
        return checkWithPattern(ValidationPattern.EMAIL_PATTERN, email);
    }

    public boolean checkCareerStartYear(String year){
        return checkWithPattern(ValidationPattern.NUMBER_PATTERN, year);
    }
}
