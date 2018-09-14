package core;

import java.awt.Color;
import java.util.HashMap;

import commands.Command;

public class CommandHandler {

	public static final CommandParser parse = new CommandParser();
	public static HashMap<String, Command> commands = new HashMap<>();
	
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
			ConsoleLogger.command("Command \""+cmd.invoke+"\" does not exist!");
			Main.sendInformationMessage(cmd.event.getTextChannel(), Color.red, "Command \""+cmd.invoke+"\" does not exist!", 5000);
		}
		
	}
	
}
