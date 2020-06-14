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
        commands.put(AjaxCommandName.CREATE_CREW, new CreateCrew());
    }

    public Command getCommand(String name){

        AjaxCommandName commandName;
        Command command = null;

        try {
            commandName = AjaxCommandName.valueOf(name.toUpperCase());
            command = commands.get(commandName);
        }catch (IllegalArgumentException e){
          //
        }
        return command;
    }

    public static AjaxCommandProvider getInstance(){
        return instance;
    }
}
