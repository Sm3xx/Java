package commands;

import core.ConsoleLogger;
import core.ErrorHandler;
import core.Main;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ping implements Command{

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		try {
			Member member = Main.getGuildMember(event.getAuthor());
			if (member != null) {
				return false;
			}
		} catch (Exception e) {
			return true;
		}
		return true;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		
		event.getChannel().sendMessage("Pong!").queue();
		return true;
		
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		ConsoleLogger.command("Ping called by "+name+" [Executed: "+success+"]");
		ConsoleLogger.info("Answered with pong");
		
	}
	
	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		ConsoleLogger.error("Ticket called by "+name+" [Executed: "+success+"]");

		ErrorHandler.cmdErr(event, "Error in runtime - Command: PING");
		
		ConsoleLogger.message("Errormessage send to "+name);
	}

	@Override
	public String help(MessageReceivedEvent event) {
		return null;
	}

	
	
}
