package commands;

import java.awt.Color;

import commands.core.CommandBase;
import commands.core.ICommand;
import core.Logger;
import core.Main;
import core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.STATIC;

public class Ping extends CommandBase implements ICommand{
	
	public Ping (String cmdName, String syntax, String helptxt) {
		super(cmdName, syntax, helptxt);
	}

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return true;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		MessageBuilder.sendInformationMessage(event.getChannel(), Color.green, "Pong! "+STATIC.PING_PONG, 5000);
		return true;
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		Logger.command(this.cmdName + " called by "+name+" [Executed: "+success+"]");
		
		Logger.info("Answered with pong");
	}
	
	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		Logger.error(this.cmdName+ " called by "+name+" [Executed: "+success+"]");

		Logger.message("Errormessage send to "+name);
	}
	
}
