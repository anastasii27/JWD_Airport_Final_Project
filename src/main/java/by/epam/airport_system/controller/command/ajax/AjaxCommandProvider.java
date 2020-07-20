package by.epam.airport_system.controller.command.ajax;

import by.epam.airport_system.controller.command.Command;
import by.epam.airport_system.controller.command.ajax.impl.*;
import java.util.HashMap;
import java.util.Map;

public final class AjaxCommandProvider {
    private static final AjaxCommandProvider instance =  new AjaxCommandProvider();
    private  final Map<AjaxCommandName, Command> commands = new HashMap<>();

    private AjaxCommandProvider(){
        commands.put(AjaxCommandName.SHOW_CREW_MEMBERS, new CrewMembers());
        commands.put(AjaxCommandName.SHOW_FLIGHTS, new AirportFlight());
        commands.put(AjaxCommandName.CHECK_CREW_NAME, new CrewNameExistence());
        commands.put(AjaxCommandName.DELETE_CREW, new DeleteCrew());
        commands.put(AjaxCommandName.DELETE_CREW_MEMBER, new DeleteCrewMember());
        commands.put(AjaxCommandName.ADD_CREW_MEMBER, new AddCrewMember());
        commands.put(AjaxCommandName.FIND_MAIN_PILOT, new CrewMainPilot());
        commands.put(AjaxCommandName.FIND_FREE_PLANE, new FreePlane());
        commands.put(AjaxCommandName.FIND_COUNTRY_AIRPORT, new CountryAirport());
        commands.put(AjaxCommandName.FIND_FREE_DISPATCHER, new FreeDispatcher());
        commands.put(AjaxCommandName.CHECK_FLIGHT_NUMBER, new FlightNumberExistence());
        commands.put(AjaxCommandName.SET_CREW_FOR_FLIGHT, new SetCrewForFlight());
        commands.put(AjaxCommandName.DELETE_FLIGHT, new DeleteFlight());
        commands.put(AjaxCommandName.FLIGHT_INFO, new FlightInformation());
        commands.put(AjaxCommandName.FIND_FREE_CREW, new FreeCrew());
        commands.put(AjaxCommandName.FIND_FLIGHT, new FindFlight());
    }

    public Command getCommand(String name){
        AjaxCommandName commandName;
        Command command = null;

        try {
            commandName = AjaxCommandName.valueOf(name.toUpperCase());
            command = commands.get(commandName);
        }catch (IllegalArgumentException e){
            //command = commands.get(AjaxCommandName.NO_SUCH_COMMAND);
        }
        return command;
    }

    public static AjaxCommandProvider getInstance(){
        return instance;
    }
}
