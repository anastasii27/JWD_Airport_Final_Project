package by.epam.tr.controller.command.ajax;

import by.epam.tr.controller.command.Command;
import by.epam.tr.controller.command.ajax.impl.AirportFlight;
import by.epam.tr.controller.command.ajax.impl.CrewMembers;
import java.util.HashMap;
import java.util.Map;

public final class AjaxCommandProvider {

    private static final AjaxCommandProvider instance =  new AjaxCommandProvider();
    private  final Map<AjaxCommandName, Command> commands = new HashMap<>();

    private AjaxCommandProvider(){
        commands.put(AjaxCommandName.CREW_MEMBERS, new CrewMembers());
        commands.put(AjaxCommandName.SHOW_FLIGHTS, new AirportFlight());
    }

    public Command getCommand(String name){

        AjaxCommandName commandName;
        Command command;

        try {
            commandName = AjaxCommandName.valueOf(name.toUpperCase());
            command = commands.get(commandName);
        }catch (IllegalArgumentException e){
            command = commands.get(AjaxCommandName.NO_SUCH_COMMAND);
        }

        return command;
    }

    public static AjaxCommandProvider getInstance(){
        return instance;
    }
}
