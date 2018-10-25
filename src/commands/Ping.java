package commands;

import java.awt.Color;

import core.Logger;
import core.Main;
import core.MessageBuilder;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.STATIC;

public class Ping implements ICommand{
	
	private EmbedBuilder error = new EmbedBuilder().setColor(Color.red); 
	
	private String cmdName;
	private String syntax;
	private String helpTxt;
	
	public Ping (String cmdName, String syntax, String helptxt) {
		this.cmdName = cmdName;
		this.syntax = syntax;
		this.helpTxt = helptxt;
	}

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
		MessageBuilder.sendInformationMessage(event.getChannel(), Color.green, "Pong! "+STATIC.PING_PONG, 5000);
		return true;
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		Logger.command(this.cmdName + " called by "+name+" [Executed: "+success+"]");
		
		Logger.info("Answered with pong");
	}
	
	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		Logger.error(this.cmdName+ " called by "+name+" [Executed: "+success+"]");

		Logger.message("Errormessage send to "+name);
	}

	@Override
	public String help(MessageReceivedEvent event) {
		event.getChannel().sendMessage(error.setTitle("Syntax: "+this.syntax).setDescription(this.helpTxt).build()).queue();
		Logger.info(this.cmdName + " help called by "+Main.getUserName(Main.getGuildMember(event.getAuthor())));
		return null;
	}

	
	
}
