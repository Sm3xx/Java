package commands;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import core.ConsoleLogger;
import core.ErrorHandler;
import core.Main;
import core.embedBuilder;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.MESSAGES;
import util.STATIC;

public class ticket_gta implements Command{

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
			Guild guild = Main.getGuild(event.getAuthor());
			MessageEmbed msg = embedBuilder.buildTicket(args, event.getAuthor().getName(), STATIC.FIVE);
			Main.getChannel(STATIC.ADMIN_LOG, guild).sendMessage(msg).queue();
			Main.getChannel(STATIC.GTA_TICKETS, guild).sendMessage(msg).queue();
			return true;
		} catch (Exception e) {return false;}
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		ConsoleLogger.command("Ticket called by "+name+" [Executed: "+success+"]");
		
		if (success) {
			MessageEmbed message = new EmbedBuilder().setColor(Color.green).setDescription(MESSAGES.TICKET_CONF).build();
			
			Message msg = event.getChannel().sendMessage(message).complete();
			
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					msg.delete().queue();
				}
			}, 5000);
			
			ConsoleLogger.message("Ticket confirmation sent to "+name);
		}
	}
	
	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		ConsoleLogger.error("GTA-Ticket called by "+name+" [Executed: "+success+"]");
		
		ErrorHandler.cmdErr(event, "Error in runtime - Command: GTA-TICKET");
		
		ConsoleLogger.message("Errormessage send to "+name);
	}

	@Override
	public String help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

}
