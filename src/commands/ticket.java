package commands;

import core.Main;
import core.embedBuilder;

import java.awt.Color;

import core.ConsoleLogger;
import core.ErrorHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.MESSAGES;
import util.STATIC;

public class ticket implements Command{

	private EmbedBuilder error = new EmbedBuilder().setColor(Color.red); 
	
	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
				help(event);
				return true;
			}
		} else {
			help(event);
			return true;
		}
		
		try {
			Member member = Main.getGuildMember(event.getAuthor());
			if (member != null) {
				return false;
			}
		} catch (Exception e) {return true;}
		
		return true;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		
		try {
			String request 	= "";
			Guild guild = Main.getGuild(event.getAuthor());
			String username = Main.getUserName(Main.getGuildMember(event.getAuthor()));
			for (int i=0; i<args.length; i++) {
				request = request + args[i] + " ";
			}
			
			String message = "User: **" + username + "**\n\nRequest: **" + request +"**";
			MessageEmbed msg = embedBuilder.buildEmbed(Main.createTitle(STATIC.TICKET, Main.getTimestamp()), message, null, false);
			Main.getChannel(STATIC.ADMIN_LOG, guild).sendMessage(msg).queue();
			Main.getChannel(STATIC.ADMIN_TICKETS, guild).sendMessage(msg).queue();
			return true;
		} catch (Exception e) {return false;}
		
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		ConsoleLogger.command("Ticket called by "+name+" [Executed: "+success+"]");
		
		if (success) {
			MessageEmbed message = embedBuilder.buildEmbed(MESSAGES.CONFIRMATION_TITLE_DE, MESSAGES.TICKET_CONF_DE, MESSAGES.CONFIRMATION_TITLE_EN, MESSAGES.TICKET_CONF_EN, Color.green, true);
			Main.sendPrivateMessage(event.getAuthor(), message, "Ticket");
			ConsoleLogger.message("Ticket confirmation sent to "+name);
		}
	}
	
	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		ConsoleLogger.error("Ticket called by "+name+" [Executed: "+success+"]");
		
		ErrorHandler.cmdErr(event, "Error in runtime - Command: TICKET");
		
		ConsoleLogger.message("Errormessage send to "+name);
	}

	@Override
	public String help(MessageReceivedEvent event) {
		event.getChannel().sendMessage(error.setTitle("Syntax: /ticket <Message>").setDescription("Send a ticket to the community admins").build()).queue();
		ConsoleLogger.info("Ticket help called by "+Main.getUserName(Main.getGuildMember(event.getAuthor())));
		return null;
	}
	
}
