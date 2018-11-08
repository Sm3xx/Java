package commands.core;

import java.awt.Color;

import core.Logger;
import core.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandBase {
	protected EmbedBuilder error = new EmbedBuilder().setColor(Color.red); 
	
	protected String cmdName;
	protected String syntax;
	protected String helpTxt;
	
	public CommandBase (String cmdName, String syntax, String helptxt) {
		this.cmdName = cmdName;
		this.syntax = syntax;
		this.helpTxt = helptxt;
	}
	
	protected boolean calledHelp(String[] args, MessageReceivedEvent event) {
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
				help(event);
				return true;
			}
		} else {
			help(event);
			return true;
		}
		return false;
	}
	
	public void help(MessageReceivedEvent event) {
		event.getChannel().sendMessage(error.setTitle("Syntax: "+this.syntax).setDescription(this.helpTxt).build()).queue();
		Logger.info(this.cmdName + " help called by "+Main.getUserName(Main.getGuildMember(event.getAuthor())));
	}
	
	public void error(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		Logger.error(this.cmdName + " called by " + name + " [Executed: " + success + "]");
	}
	
	public void executed(boolean success, MessageReceivedEvent event) {
		Member member = Main.getGuildMember(event.getAuthor());
		Logger.command(this.cmdName + " called by "+Main.getUserName(member)+" [Executed: "+success+"]");
	}
}


