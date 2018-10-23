package core;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.MESSAGES;
import util.STATIC;

public class ErrorHandler {

	public static void sendErr(User user, String message_de, String message_en, String errorType) {
		message_de = message_de.replaceAll("&prefix&", STATIC.PREFIX); 
		message_en = message_en.replaceAll("&prefix&", STATIC.PREFIX);
		String title_de = Main.createTitle(STATIC.FLAG_DE, MESSAGES.ERROR_TITLE_DE);
		String title_en = Main.createTitle(STATIC.FLAG_GB, MESSAGES.ERROR_TITLE_EN);
		Main.sendPrivateMessage(user, embedBuilder.buildEmbed(title_de , message_de, title_en, message_en, Color.RED, true), errorType);
	}
	
	public static void cmdErr(MessageReceivedEvent event, String content) {
		MessageEmbed msg = new EmbedBuilder().setDescription(MESSAGES.COMMAND_ERROR).setColor(Color.RED).build();
		MessageEmbed message = new EmbedBuilder().setDescription(content).setColor(Color.RED).build();
		
		event.getChannel().sendMessage(msg).queue();
		Logger.error(content);
		
		for (String id : STATIC.DEVELOPERS) {
			User user = Main.getGuild(event.getAuthor()).getMemberById(id).getUser();
			Main.sendPrivateMessage(user, message, "Error");
		}
	}
	
}
