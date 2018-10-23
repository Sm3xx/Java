package commands;

import java.awt.Color;

import core.Logger;
import core.ErrorHandler;
import core.Main;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.STATIC;

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
		Main.sendInformationMessage(event.getChannel(), Color.green, "Pong! "+STATIC.PING_PONG, 5000);
		return true;
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		Logger.command("Ping called by "+name+" [Executed: "+success+"]");
		Logger.info("Answered with pong");
		
	}
	
	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		Logger.error("Ticket called by "+name+" [Executed: "+success+"]");

		ErrorHandler.cmdErr(event, "Error in runtime - Command: PING");
		
		Logger.message("Errormessage send to "+name);
	}

	@Override
	public String help(MessageReceivedEvent event) {
		return null;
	}

	
	
}
