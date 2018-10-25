package commands;

import java.awt.Color;

import core.Logger;
import core.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.MESSAGES;

public class Ticket implements ICommand{
	
private EmbedBuilder error = new EmbedBuilder().setColor(Color.red); 
	
	private String cmdName;
	private String syntax;
	private String helpTxt;
	
	public Ticket (String cmdName, String syntax, String helptxt) {
		this.cmdName = cmdName;
		this.syntax = syntax;
		this.helpTxt = helptxt;
	}

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
		Logger.command(this.cmdName + " called by "+name+" [Executed: "+success+"]");			
		Logger.message("Ticket link sent to "+name);
	}
	
	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		Logger.error(this.cmdName + " called by "+name+" [Executed: "+success+"]");
	}

	@Override
	public String help(MessageReceivedEvent event) {
		event.getChannel().sendMessage(error.setTitle("Syntax: "+this.syntax).setDescription(this.helpTxt).build()).queue();
		Logger.info(this.cmdName + " help called by "+Main.getUserName(Main.getGuildMember(event.getAuthor())));
		return null;
	}

}
