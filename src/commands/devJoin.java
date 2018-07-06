package commands;

import java.awt.Color;

import core.Main;
import core.embedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.MESSAGES;
import util.STATIC;

public class devJoin implements Command{

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		event.getAuthor().openPrivateChannel().queue((channel) ->
        {
        	MessageEmbed content = embedBuilder.buildEmbed(MESSAGES.IMPORTANT_TITLE, STATIC.FLAG_DE+" "+MESSAGES.JOINTITLE_DE, MESSAGES.JOINMESSAGE_DE, STATIC.FLAG_GB+" "+MESSAGES.JOINTITLE_EN, MESSAGES.JOINMESSAGE_EN, Color.white, true);
    		String id = channel.sendMessage(content).complete().getId();
    		
    		channel.addReactionById(id, STATIC.FLAG_DE).queue();
    		channel.addReactionById(id, STATIC.FLAG_GB).queue();
    		channel.addReactionById(id, STATIC.CONTROLLER).queue();
        });
		
		TextChannel adminlog = Main.getChannel(STATIC.ADMIN_LOG, event.getGuild());
		TextChannel waiting = Main.getChannel(STATIC.WAITING_ROOM, event.getGuild());
		
		MessageEmbed content = embedBuilder.buildEmbed("Willkommen "+Main.getUserName(event.getMember()), MESSAGES.WAITING_ROOM_DE, "Welcome "+Main.getUserName(event.getMember()), MESSAGES.WAITING_ROOM_EN, Color.WHITE, true);
		waiting.sendMessage(content);
		
		
		String msg = "**" + event.getMember().getEffectiveName() + "** joined!";
		adminlog.sendMessage(embedBuilder.buildEmbed(STATIC.NEW + " " + Main.getTimestamp(), msg, null, false)).queue();
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

	@Override
	public String help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

}
