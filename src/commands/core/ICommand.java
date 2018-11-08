package commands.core;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface ICommand {
	
	/**
	 * Checks if the command can be executed without error. Return false to execute command.
	 * @param args
	 * @param event
	 * @return boolean
	 */
	boolean called(String[] args, MessageReceivedEvent event);
	
	/**
	 * Executes the command and returns a bool. If execution was successful it returns true.
	 * @param args
	 * @param event
	 * @return boolean
	 */
	boolean action(String[] args, MessageReceivedEvent event);
	
	/**
	 * Gets called if command execution was successful.
	 * @param success
	 * @param event
	 */
	void executed(boolean success, MessageReceivedEvent event);
	void error(boolean success, MessageReceivedEvent event);

}
