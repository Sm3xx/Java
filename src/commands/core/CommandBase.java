package commands.core;

import java.awt.Color;
import java.util.List;

import core.Logger;
import core.handlers.GuildHandler;
import core.handlers.MemberHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class CommandBase {
	
	protected EmbedBuilder error = new EmbedBuilder().setColor(Color.red); 
	
	protected String cmdName;
	protected String syntax;
	protected String helpTxt;
	
	public CommandBase (String cmdName, String syntax, String helptxt) {
		this.cmdName = cmdName;
		this.syntax = syntax;
		this.helpTxt = helptxt;
	}
	
	/**
	 * Check if the given Member has the permission to execute the following code
	 * @param member
	 * @param permissions
	 * @return true/false
	 */
	protected boolean checkPermission(Member member, String[] permissions) {
		List<Role> roleList = member.getRoles();
		for (String p : permissions) {
			for (Role r : roleList) {
				if (r.getName().equalsIgnoreCase(p)) {
					return true;
				}
			}
		}
		return false;
	}
	
	protected String getUsername(User u) {
		return MemberHandler.getUserName(GuildHandler.getGuildMember(u));
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
		Logger.info(this.cmdName + " help called by "+getUsername(event.getAuthor()));
	}
	
	public void error(boolean success, MessageReceivedEvent event) {
		Logger.error(this.cmdName + " called by " + getUsername(event.getAuthor()) + " [Executed: " + success + "]");
	}
	
	public void executed(boolean success, MessageReceivedEvent event) {
		Logger.command(this.cmdName + " called by "+getUsername(event.getAuthor())+" [Executed: "+success+"]");
	}
}


