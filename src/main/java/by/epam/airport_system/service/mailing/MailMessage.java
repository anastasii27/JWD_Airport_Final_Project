package by.epam.airport_system.service.mailing;

public final class MailMessage {

    private MailMessage(){
    }

    public static final String PASSWORD_CHANGE = "Dear user!\nYour password has been changed.\n" +
            "If you didn`t change the password, please contact us.\nBlueSky support team";
    public static final String LOGIN_CHANGE = "Dear user!\nYour login has been changed.\n" +
            "If you didn`t change the login, please contact us.\nBlueSky support team";
}