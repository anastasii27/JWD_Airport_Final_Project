package by.epam.tr.controller.command.ajax;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.command.ajax.impl.*;
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
        commands.put(AjaxCommandName.FIND_MAIN_PILOT, new FindCrewMainPilot());
        commands.put(AjaxCommandName.FIND_FREE_PLANE, new FindFreePlanes());
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
