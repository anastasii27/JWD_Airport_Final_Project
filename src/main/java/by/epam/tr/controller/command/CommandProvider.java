package by.epam.tr.controller.command;

import by.epam.tr.controller.command.impl.NoSuchCommand;
import by.epam.tr.controller.command.impl.Registration;
import by.epam.tr.controller.command.impl.SingIn;
import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {

    private static final CommandProvider instance =  new CommandProvider();
    private  final Map <CommandName, Command> commands = new HashMap<>();

    public CommandProvider(){
        commands.put(CommandName.SING_IN, new SingIn());
        commands.put(CommandName.REGISTER, new Registration());
        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());

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
