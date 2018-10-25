package core;

import java.awt.Color;
import java.util.HashMap;

import commands.ICommand;

public class CommandHandler {

	public static final CommandParser parse = new CommandParser();
	public static HashMap<String, ICommand> commands = new HashMap<>();
	
	public static void handleCommand(CommandParser.commandContainer cmd) {
		
		if (commands.containsKey(cmd.invoke)) {
			boolean safe = !commands.get(cmd.invoke).called(cmd.args, cmd.event);
			if (safe) {
				boolean success = commands.get(cmd.invoke).action(cmd.args, cmd.event);
				if (success) {
					commands.get(cmd.invoke).executed(success, cmd.event);					
				} else {
					commands.get(cmd.invoke).error(success, cmd.event);
				}
			} else {
				commands.get(cmd.invoke).executed(safe, cmd.event);
			}
		} else {
			Logger.command("Command \""+cmd.invoke+"\" does not exist!");
			MessageBuilder.sendInformationMessage(cmd.event.getTextChannel(), Color.red, "Command \""+cmd.invoke+"\" does not exist!", 5000);
		}
		
	}
	
	public static void registerCommand(String[] invokes, ICommand command) {
		for (String invoke : invokes) {
			CommandHandler.commands.put(invoke, command);
		}
	}
	
	public static void registerCommand(String invoke, ICommand command) {
		CommandHandler.commands.put(invoke, command);
	}
	
}
