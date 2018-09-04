package commands;

import java.awt.Color;

import core.Main;
import core.embedBuilder;
import net.dv8tion.jda.core.EmbedBuilder;
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
        	MessageEmbed content = new EmbedBuilder().setDescription(MESSAGES.CHOOSE_LANG).setTitle(MESSAGES.CHOOSE_LANG_TITLE).setColor(Color.green).build();
    		String id = channel.sendMessage(content).complete().getId();
    		
    		channel.addReactionById(id, STATIC.FLAG_DE).queue();
    		channel.addReactionById(id, STATIC.FLAG_GB).queue();
        });
		
		TextChannel adminlog = Main.getChannel(STATIC.ADMIN_LOG, Main.getGuild(event.getAuthor()));
		TextChannel waiting = Main.getChannel(STATIC.WAITING_ROOM, Main.getGuild(event.getAuthor()));
		
		MessageEmbed content = embedBuilder.buildEmbed("Willkommen "+Main.getUserName(event.getMember()), MESSAGES.WAITING_ROOM_DE, "Welcome "+Main.getUserName(event.getMember()), MESSAGES.WAITING_ROOM_EN, Color.WHITE, true);
		waiting.sendMessage(event.getAuthor().getAsMention()+" joined").queue();
		waiting.sendMessage(content).queue();
		
		
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
