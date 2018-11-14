package commands;

import java.awt.Color;
import java.util.List;

import commands.core.CommandBase;
import commands.core.ICommand;
import core.Logger;
import core.Main;
import core.MessageBuilder;
import core.handlers.GuildHandler;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.EMOTES;
import util.PERMISSIONS;
import util.STATIC;

public class Prune extends CommandBase implements ICommand{
	private int count = 0;
	
	public Prune (String cmdName, String syntax, String helptxt) {
		super(cmdName, syntax, helptxt);
	}
	
	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		if(calledHelp(args, event)) {
			return true;
		}
		
		if (getCount(args[0]) < 2 || getCount(args[0]) > 100) {
			MessageBuilder.sendInformationMessage(event.getChannel(), Color.RED, "You can only delete 2 up to 100 messages", 5000);;
			return true;
		}
		
		if (checkPermission(event, event.getMember(), PERMISSIONS.ADMIN_COMMAND)) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		
		count = getCount(args[0]);
		
		try {
			MessageHistory history = new MessageHistory(event.getTextChannel());
			List<Message> msgList;
			
			event.getMessage().delete().queue();
			
			msgList = history.retrievePast(count).complete();
			event.getTextChannel().deleteMessages(msgList).queue();
			
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		Logger.command(this.cmdName + " called by "+getUsername(event.getAuthor())+" [Executed: "+success+"]");
		
		if (success) {
			Logger.info(count + " messages deleted!");
			MessageBuilder.sendInformationMessage(event.getTextChannel(), Color.green, count + " messages deleted!", 5000);
			
			TextChannel adminlog = GuildHandler.getChannel(STATIC.ADMIN_LOG);
			String message = "**" + event.getMember().getEffectiveName() + "** deleted **"+count+"** Messages in **"+event.getChannel().getName()+"**";
			adminlog.sendMessage(MessageBuilder.buildEmbed(EMOTES.INFO + " " + Main.getTimestamp(), message, null, false)).queue();
		}
		
	}

	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		Logger.error(this.cmdName+ " called by "+getUsername(event.getAuthor())+" [Executed: "+success+"]");
		MessageBuilder.sendInformationMessage(event.getTextChannel(), Color.red, "Only Messages not older than 14 Days are deleteable.", 10000);
	}
	
	
	private int getCount(String string) {
		try {
			return Integer.parseInt(string);
		} catch (Exception e) {
			return 0;
		}
	}

}
