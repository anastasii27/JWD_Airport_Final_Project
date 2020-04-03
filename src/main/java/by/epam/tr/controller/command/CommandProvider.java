package by.epam.tr.controller.command;

import by.epam.tr.controller.command.impl.*;
import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {

    private static final CommandProvider instance =  new CommandProvider();
    private  final Map <CommandName, Command> commands = new HashMap<>();

    public CommandProvider(){
        commands.put(CommandName.SIGN_IN, new SignIn());
        commands.put(CommandName.REGISTER, new Registration());
        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
        commands.put(CommandName.SIGN_OUT, new SignOut());
        commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguage());
    }

    public Command getCommand(String name){
        CommandName commandName;
        Command command;

        try {
            commandName = CommandName.valueOf(name.toUpperCase());
            command = commands.get(commandName);
        }catch (IllegalArgumentException e){
            command = commands.get(CommandName.NO_SUCH_COMMAND);
        }

        return command;
    }

    public static CommandProvider getInstance(){
        return instance;
    }
}
