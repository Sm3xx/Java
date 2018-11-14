package commands;

import commands.core.CommandBase;
import commands.core.ICommand;
import containers.ExceptionContainer;
import core.Logger;
import core.handlers.RoleHandler;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.PERMISSIONS;

public class ReloadRoleNames extends CommandBase implements ICommand {

	public ReloadRoleNames(String cmdName, String syntax, String helptxt) {
		super(cmdName, syntax, helptxt);
	}

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		if (checkPermission(event, event.getMember(), PERMISSIONS.ADMIN_COMMAND)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		ExceptionContainer error = RoleHandler.loadRoleNames();
		if (error != null) {
			TextChannel tc = event.getTextChannel();
			tc.sendMessage(this.error.setDescription("File \"roles.txt\" could not be loaded!").build()).queue();
			RoleHandler.printRolenames(tc);
			return false;
		} else {
			RoleHandler.printRolenames(event.getTextChannel());
			return true;
		}
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		Logger.command(this.cmdName + " called by " + getUsername(event.getAuthor()) + " [Executed: " + success + "]");
	}

}
