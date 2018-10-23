package commands;

import java.awt.Color;

import core.Logger;
import core.ErrorHandler;
import core.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.MESSAGES;

public class ticket implements Command{

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return false;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		try {
			MessageEmbed msg = new EmbedBuilder()
					.setDescription(MESSAGES.TICKET_MESSAGE_DE+"\n\n"+MESSAGES.TICKET_MESSAGE_EN+"\n\n"+MESSAGES.TICKET_LINK)
					.setColor(Color.green)
					.build();
			event.getChannel().sendMessage(msg).queue();
			return true;
		} catch (Exception e) {return false;}
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		Logger.command("Ticket called by "+name+" [Executed: "+success+"]");			
		Logger.message("Ticket link sent to "+name);
	}
	
	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		Logger.error("GTA-Ticket called by "+name+" [Executed: "+success+"]");
		
		ErrorHandler.cmdErr(event, "Error in runtime - Command: TICKET");
		
		Logger.message("Errormessage send to "+name);
	}

	@Override
	public String help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

}
