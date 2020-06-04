package by.epam.tr.controller.command;

public enum CommandName {

    SIGN_IN ("all"),
    REGISTER ("all"),
    NO_SUCH_COMMAND ("all"),
    SIGN_OUT("authorized"),
    CHANGE_LANGUAGE ("all"),
    SHOW_REGISTER_PAGE ("all"),
    SHOW_SIGN_IN_PAGE ("all"),
    SHOW_USER_PAGE ("authorized"),
    SHOW_FLIGHTS_PAGE ("authorized"),
    SHOW_FLIGHT_INFO("authorized"),
    SHOW_MY_FLIGHTS("authorized"),
    SHOW_MY_CREWS("authorized");


    private final String accessType;

    private CommandName(String accessType){
        this.accessType = accessType;
    }

    public String getAccessType() {
        return accessType;
    }
}


