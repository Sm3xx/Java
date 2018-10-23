package commands;

import java.awt.Color;
import java.util.List;
import core.Main;
import core.embedBuilder;
import core.Logger;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.PERMISSIONS;
import util.STATIC;

public class prune implements Command{
	
	private EmbedBuilder error = new EmbedBuilder().setColor(Color.red); 
	
	private int count = 0;
	
	// if this returns true the command will not be executed
	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
				help(event);
				return true;
			}
		} else {
			help(event);
			return true;
		}
		
		if (getCount(args[0]) < 2 || getCount(args[0]) > 100) {
			Main.sendInformationMessage(event.getChannel(), Color.RED, "You can only delete 2 up to 100 messages", 5000);;
			return true;
		}
		
		if (Main.checkPermission(event.getMember(), PERMISSIONS.PRUNE)) {
			return false;
		} else {
			Main.sendInformationMessage(event.getChannel(), Color.red, "Missing permissions!", 5000);			
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
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		Logger.command("Prune called by "+name+" [Executed: "+success+"]");
		
		if (success) {
			Logger.info(count + " messages deleted!");
			Main.sendInformationMessage(event.getTextChannel(), Color.green, count + " messages deleted!", 5000);
			
			TextChannel adminlog = Main.getChannel(STATIC.ADMIN_LOG, event.getGuild());
			String message = "**" + event.getMember().getEffectiveName() + "** deleted **"+count+"** Messages in **"+event.getChannel().getName()+"**";
			adminlog.sendMessage(embedBuilder.buildEmbed(STATIC.INFO + " " + Main.getTimestamp(), message, null, false)).queue();
		}
		
	}

	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		Logger.error("Prune called by "+name+" [Executed: "+success+"]");
		
		Main.sendInformationMessage(event.getTextChannel(), Color.red, "Only Messages not older than 14 Days are deleteable.", 10000);
		
	}
	
	@Override
	public String help(MessageReceivedEvent event) {
		event.getChannel().sendMessage(error.setTitle("Syntax: /prune <MessageCount>").setDescription("Delete multiple messages in one channel at once").build()).queue();
		Logger.info("Prune help called by "+Main.getUserName(Main.getGuildMember(event.getAuthor())));
		return null;
	}
	
	
	
	private int getCount(String string) {
		try {
			return Integer.parseInt(string);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
