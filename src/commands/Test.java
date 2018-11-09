package commands;

import commands.core.ICommand;
import core.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Test implements ICommand{

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		System.out.println(Main.getJDA().getGuilds().get(0).getName());
		return false;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return false;
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
