package by.epam.tr.controller.command;

import by.epam.tr.controller.command.impl.*;
import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {

    private static final CommandProvider instance =  new CommandProvider();
    private  final Map <CommandName, Command> commands = new HashMap<>();

    private CommandProvider(){
        commands.put(CommandName.SIGN_IN, new SignIn());
        commands.put(CommandName.REGISTER, new Registration());
        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
        commands.put(CommandName.SIGN_OUT, new SignOut());
        commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguage());
        commands.put(CommandName.SHOW_REGISTER_PAGE, new RegisterPage());
        commands.put(CommandName.SHOW_SIGN_IN_PAGE, new SignInPage());
        commands.put(CommandName.SHOW_USER_PAGE, new UserPage());
        commands.put(CommandName.SHOW_FLIGHTS_PAGE, new AllFlights());
        commands.put(CommandName.SHOW_FLIGHT_INFO, new FlightInformation());
        commands.put(CommandName.SHOW_MY_FLIGHTS, new MyFlights());
        commands.put(CommandName.SHOW_MY_CREWS, new MyCrews());
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
