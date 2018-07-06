package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface Command {
	
	boolean called(String[] args, MessageReceivedEvent event); // return false to execute command
	boolean action(String[] args, MessageReceivedEvent event); // return true to call "executed"
	void executed(boolean success, MessageReceivedEvent event);
	void error(boolean success, MessageReceivedEvent event);
	String help(MessageReceivedEvent event);

}
