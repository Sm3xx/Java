package commands;

import commands.core.CommandBase;
import commands.core.ICommand;
import listeners.MemberJoinListener;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.PERMISSIONS;

public class DevJoin extends CommandBase implements ICommand{

	public DevJoin(String cmdName, String syntax, String helptxt) {
		super(cmdName, syntax, helptxt);
	}

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		if (checkPermission(event, event.getMember(), PERMISSIONS.DEV)) {
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
