package commands;

import commands.core.ICommand;
import core.Main;
import listeners.MemberJoinListener;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.PERMISSIONS;

public class DevJoin implements ICommand{

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		if (Main.checkPermission(event.getMember(), PERMISSIONS.DEV)) {
			return false;			
		}
		return true;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		MemberJoinListener.execJoinEvent(event.getMember(), event.getGuild());
		return true;
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		
	}

}
