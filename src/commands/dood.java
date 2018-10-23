package commands;

import java.awt.Color;

import core.ExceptionContainer;
import core.Logger;
import core.Main;
import core.roleHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class dood implements Command{

	private String rolename = "";
	private int count = 0;
	
	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		if (args.length == 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		String role = "";
		for (int i=0; i<args.length; i++) {
			role = role + args[i] + " ";
		}
		// TODO alle entfernen die diese rolle schon haben
		count = event.getGuild().getMembers().size();
		rolename = role;
		role = role.replaceAll("@", "").trim();
		ExceptionContainer error = roleHandler.bulkAddRole(event.getGuild(), event.getGuild().getMembers(), role);
		if (error == null) {
			return true;
		} else {
			Logger.error(error.getMessage());
			return false;
		}
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		Main.sendInformationMessage(event.getChannel(), Color.WHITE, rolename + " added to " + count + " members" , 5000);
	}

	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
