package commands;

import java.awt.Color;
import java.util.List;

import containers.ExceptionContainer;
import core.Logger;
import core.Main;
import core.MessageBuilder;
import core.RoleHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.PERMISSIONS;

public class AddServerRole implements ICommand{

	private String rolename = "";
	private ExceptionContainer ex;
	private EmbedBuilder error = new EmbedBuilder().setColor(Color.red); 
	
	private String cmdName;
	private String syntax;
	private String helpTxt;
	
	public AddServerRole (String cmdName, String syntax, String helptxt) {
		this.cmdName = cmdName;
		this.syntax = syntax;
		this.helpTxt = helptxt;
	}
	
	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		if (Main.checkPermission(event.getMember(), PERMISSIONS.ADMIN_COMMAND)) {
			if (args.length == 1) {
				return false;
			} else {
				MessageBuilder.sendInformationMessage(event.getChannel(), Color.RED, "No Role specified!");
			}
		} else {
			MessageBuilder.sendInformationMessage(event.getChannel(), Color.RED, "Missing Permissions");
		}
		return true;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		String rolename = "";
		List<Member> members = event.getGuild().getMembers();
		for (int i=0; i<args.length; i++) {
			rolename = rolename + args[i] + " ";
		}
		
		this.rolename = rolename.replaceAll("@", "").trim();
		ExceptionContainer error = RoleHandler.bulkAddRole(event.getGuild(), members, this.rolename);
		if (error == null) {
			return true;
		} else {
			Logger.error(error.getMessage());
			this.ex = error;
			return false;
		}
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		Logger.command(this.cmdName + " called by "+name+" [Executed: "+success+"]");
		
		if (success) {
			MessageBuilder.sendInformationMessage(event.getChannel(), Color.WHITE, rolename + " added to all members");
		}
	}

	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		Logger.error(this.cmdName+ " called by "+name+" [Executed: "+success+"]");
		
		MessageBuilder.sendInformationMessage(event.getChannel(), Color.RED, "An error occured while executing this command.\n\n**[ErrorMessage]**\n" + ex.getMessage());		
	}

	@Override
	public String help(MessageReceivedEvent event) {
		event.getChannel().sendMessage(error.setTitle("Syntax: "+this.syntax).setDescription(this.helpTxt).build()).queue();
		Logger.info(this.cmdName + " help called by "+Main.getUserName(Main.getGuildMember(event.getAuthor())));
		return null;
	}
	
	

}
