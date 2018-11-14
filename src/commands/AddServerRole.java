package commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import commands.core.CommandBase;
import commands.core.ICommand;
import containers.ExceptionContainer;
import core.Logger;
import core.MessageBuilder;
import core.handlers.GuildHandler;
import core.handlers.RoleHandler;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.PERMISSIONS;

public class AddServerRole extends CommandBase implements ICommand {

	private Role role = null;
	private int count = 0;
	private String rolename = "";
	private ExceptionContainer ex;

	public AddServerRole(String cmdName, String syntax, String helptxt) {
		super(cmdName, syntax, helptxt);
	}

	private Role getRole(String[] args) {
		rolename = "";
		for (int i = 0; i < args.length; i++) {
			rolename = rolename + args[i] + " ";
		}

		rolename = rolename.replaceAll("@", "").trim();
		Role r = RoleHandler.getRole(GuildHandler.getGuild(), rolename);

		if (r != null)
			return r;
		else
			return null;
	}

	private List<Member> getMembersToAddRole(List<Member> m, Role r) {
		List<Member> memToAdd = new ArrayList<Member>();

		for (Member member : m) {
			if (!(member.getRoles().contains(r)) && !(member.getUser().isBot())) {
				memToAdd.add(member);
			}
		}
		return memToAdd;
	}

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {

		if (calledHelp(args, event)) {
			return true;
		}

		if (checkPermission(event, event.getMember(), PERMISSIONS.ADMIN_COMMAND)) {
			if (args.length == 1) {
				role = getRole(args);
				if (role != null) {
					return false;
				} else {
					MessageBuilder.sendInformationMessage(event.getChannel(), Color.RED, "Enter a valid rolename");
				}
			} else {
				MessageBuilder.sendInformationMessage(event.getChannel(), Color.RED, "No Role specified!");
			}
		}
		return true;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		List<Member> members = getMembersToAddRole(event.getGuild().getMembers(), role);

		ExceptionContainer error = RoleHandler.bulkAddRole(event.getGuild(), members, role);
		if (error == null) {
			count = members.size();
			return true;
		} else {
			Logger.error(error.getMessage());
			this.ex = error;
			return false;
		}
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		Logger.command(this.cmdName + " called by " + getUsername(event.getAuthor()) + " [Executed: " + success + "]");

		if (success) {
			MessageBuilder.sendInformationMessage(event.getChannel(), Color.WHITE,
					rolename + " added to " + count + " members");
		}
	}

	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		Logger.error(this.cmdName + " called by " + getUsername(event.getAuthor()) + " [Executed: " + success + "]");

		MessageBuilder.sendInformationMessage(event.getChannel(), Color.RED,
				"An error occured while executing this command.\n\n**[ErrorMessage]**\n" + ex.getMessage());
	}

}
