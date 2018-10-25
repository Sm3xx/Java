package commands;

import java.awt.Color;

import core.Main;
import core.MessageBuilder;
import core.Logger;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.RULES;

public class Rules_en implements ICommand{
	
	private EmbedBuilder error = new EmbedBuilder().setColor(Color.red); 
	
	private String cmdName;
	private String syntax;
	private String helpTxt;
	
	public Rules_en (String cmdName, String syntax, String helptxt) {
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
		
		try {
			MessageEmbed msg = MessageBuilder.buildEmbed(RULES.EN_TITLE, RULES.EN, Color.WHITE, true);
			if (event.getPrivateChannel() != null) {
				event.getChannel().sendMessage(msg).queue();
			} else {
				Member member = Main.getGuildMember(event.getAuthor());
				MessageBuilder.sendPrivateMessage(member.getUser(), msg, this.cmdName);
			}
			return true;
		} catch (Exception e ) {
			return false;
		}
		
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		Member member = Main.getGuildMember(event.getAuthor());
		Logger.command(this.cmdName + " called by "+Main.getUserName(member)+" [Executed: "+success+"]");
		Logger.message("Rules sent to "+Main.getUserName(member));
		
	}
	
	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		Logger.error(this.cmdName+ " called by "+name+" [Executed: "+success+"]");
	}

	@Override
	public String help(MessageReceivedEvent event) {
		event.getChannel().sendMessage(error.setTitle("Syntax: "+this.syntax).setDescription(this.helpTxt).build()).queue();
		Logger.info(this.cmdName + " help called by "+Main.getUserName(Main.getGuildMember(event.getAuthor())));
		return null;
	}

}
