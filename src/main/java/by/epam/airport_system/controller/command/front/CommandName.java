package by.epam.airport_system.controller.command.front;

import by.epam.airport_system.bean.Role;

public enum CommandName {
    SIGN_IN (Role.NO_ROLE),
    REGISTER (Role.NO_ROLE),
    NO_SUCH_COMMAND (Role.NO_ROLE),
    SIGN_OUT (Role.NO_ROLE),
    CHANGE_LANGUAGE (Role.NO_ROLE),
    SHOW_REGISTER_PAGE (Role.NO_ROLE),
    SHOW_SIGN_IN_PAGE (Role.NO_ROLE),
    SHOW_USER_PAGE (Role.NO_ROLE),
    SHOW_DEPARTURES_ARRIVALS (Role.NO_ROLE),
    SHOW_MY_FLIGHTS (Role.PILOT, Role.STEWARD),
    SHOW_CREW_PAGE (Role.DISPATCHER),
    SHOW_CREATE_CREW_PAGE (Role.DISPATCHER),
    CREATE_CREW (Role.DISPATCHER),
    SHOW_DISPATCHER_FLIGHTS (Role.DISPATCHER),
    SHOW_FLIGHT_MANAGEMENT_PAGE (Role.ADMIN),
    SHOW_CREATE_FLIGHT_PAGE( Role.ADMIN),
    CREATE_FLIGHT (Role.ADMIN),
    FREE_CREWS_FOR_FLIGHT (Role.ADMIN),
    EDIT_FLIGHT (Role.ADMIN),
    SHOW_FLIGHT_TIMETABLE_PAGE (Role.NO_ROLE),
    EDIT_USER (Role.ADMIN, Role.DISPATCHER, Role.PILOT, Role.STEWARD),
    SHOW_USER_EDITING_PAGE (Role.ADMIN, Role.DISPATCHER, Role.PILOT, Role.STEWARD),
    CHANGE_LOGIN (Role.ADMIN, Role.DISPATCHER, Role.PILOT, Role.STEWARD),
    CHANGE_PASSWORD (Role.ADMIN, Role.DISPATCHER, Role.PILOT, Role.STEWARD);

    private final Role [] roles;

    private CommandName(Role...roles){
        this.roles = roles;
    }

    public Role[] getRoles() {
        return roles;
    }
}


