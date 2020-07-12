package by.epam.tr.controller.command.front;

public enum CommandName {
    SIGN_IN ("common"),
    REGISTER ("common"),
    NO_SUCH_COMMAND ("common"),
    SIGN_OUT("authorized"),
    CHANGE_LANGUAGE ("common"),
    SHOW_REGISTER_PAGE ("common"),
    SHOW_SIGN_IN_PAGE ("common"),
    SHOW_USER_PAGE ("authorized"),
    SHOW_DEPARTURES_ARRIVALS ("both"),
    SHOW_MY_FLIGHTS("authorized"),
    SHOW_CREW_PAGE("authorized"),
    SHOW_CREATE_CREW_PAGE("authorized"),
    CREATE_CREW("authorized"),
    SHOW_DISPATCHER_FLIGHTS("authorized"),
    SHOW_FLIGHT_MANAGEMENT_PAGE("authorized"),
    SHOW_CREATE_FLIGHT_PAGE("authorized"),
    CREATE_FLIGHT("authorized"),
    FREE_CREWS_FOR_FLIGHT("authorized"),
    EDIT_FLIGHT("authorized"),
    SHOW_FLIGHT_TIMETABLE_PAGE("common");

    private final String accessType;

    private CommandName(String accessType){
        this.accessType = accessType;
    }

    public String getAccessType() {
        return accessType;
    }
}


