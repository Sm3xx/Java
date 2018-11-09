package core;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import containers.MessageContainer;
import core.handlers.GuildHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import util.STATIC;

public class MessageBuilder {
	
	/**
	 * Creates string for the Title field of an embeded Message
	 * @param emote
	 * @param titleContent
	 * @return String
	 */
	public static String createTitle(String emote, String titleContent) {	
		String title = emote + " " + titleContent;
		return title;
	}
	
	/**
	 * Create an embeded Message with a Headline and two textfields
	 * @param text_title_de
	 * @param text_de
	 * @param text_title_en
	 * @param text_en
	 * @param color
	 * @param footer
	 * @return MessageEmbed
	 */
	public static MessageEmbed buildEmbed(String text_title_de, String text_de, String text_title_en, String text_en, Color color, boolean footer) {
		
		// 2 Languages without title
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(color);
		builder.addField(text_title_de, text_de.replaceAll("&prefix&", STATIC.PREFIX), false);
		builder.addBlankField(false);
		builder.addField(text_title_en, text_en.replaceAll("&prefix&", STATIC.PREFIX), false);
		if(footer) { 
			builder.setFooter("Bot developed by Veteran-Gaming", null);
		}
		return builder.build();
	}
	
	/**
	 * Create an embeded Message with two textfields
	 * @param title
	 * @param text_title_de
	 * @param text_de
	 * @param text_title_en
	 * @param text_en
	 * @param color
	 * @param footer
	 * @return MessageEmbed
	 */
	public static MessageEmbed buildEmbed(String title, String text_title_de, String text_de, String text_title_en, String text_en, Color color, boolean footer) {
		
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(color);
		builder.setTitle(title);
		builder.addField(text_title_de, text_de.replaceAll("&prefix&", STATIC.PREFIX), false);
		builder.addBlankField(false);
		builder.addField(text_title_en, text_en.replaceAll("&prefix&", STATIC.PREFIX), false);
		if(footer) { 
			builder.setFooter("Bot developed by Veteran-Gaming", null);
		}
		
		return builder.build();
	}
	
	/**
	 * Create an embeded Message with one textfield
	 * @param title
	 * @param text
	 * @param color
	 * @param footer
	 * @return MessageEmbed
	 */
	public static MessageEmbed buildEmbed(String title, String text, Color color, boolean footer) {
		
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(color);
		builder.addField(title, text.replaceAll("&prefix&", STATIC.PREFIX), false);
		if(footer) { 
			builder.setFooter("Bot developed by Veteran-Gaming", null);
		}
		
		return builder.build();
	}
	
	/**
	 * Create an embeded Message designed for a ticket system
	 * @param args
	 * @param author
	 * @param icon
	 * @return MessageEmbed
	 */
	public static MessageEmbed buildTicket(String[] args, String author, String icon) {
		String request 	= "";
		for (int i=0; i<args.length; i++) {
			request = request + args[i] + " ";
		}
		
		String message = "User: **" + author + "**\n\nRequest: **" + request +"**";
		MessageEmbed msg = MessageBuilder.buildEmbed(createTitle(icon, Main.getTimestamp()), message, null, false);
		return msg;
	}
	
	/**
	 * Send message to the adminlog
	 * @param text
	 * @param emote
	 * @param channel
	 */
	public static void sendAdminLog(String text, String emote) {
		TextChannel adminlog = GuildHandler.getAdminlog();
		if (adminlog != null) {
			String title = emote + " "+ Main.getTimestamp();
			adminlog.sendMessage(buildEmbed(title, text, null, false)).queue();
		}
	}

	/**
	 * Send a message to an user in a private channel
	 * @param user
	 * @param content
	 * @param messageType
	 * @return MessageContainer object
	 */
	public static MessageContainer sendPrivateMessage(User user, MessageEmbed content, String messageType) {
		PrivateChannel channel = user.openPrivateChannel().complete();
		String id = channel.sendMessage(content).complete().getId();
		return new MessageContainer(channel, id, null);
	}

	/**
	 * Send a message to an user in a private channel and let the bot react with the reactions given in the array 
	 * @param user
	 * @param content
	 * @param reactions
	 * @return MessageContainer object
	 */
	public static MessageContainer sendReactionMessage(User user, MessageEmbed content, String[] reactions) {
		PrivateChannel channel = user.openPrivateChannel().complete();
		String id = channel.sendMessage(content).complete().getId();
		
		if (reactions != null) {
    		for (String reaction : reactions) {
    			channel.addReactionById(id, reaction).queue();
    		}
		}
		return new MessageContainer(channel, id, reactions);
	}
	
	public static void sendInformationMessage(TextChannel channel, Color color, String message, Integer timer) {
		Message msg = channel.sendMessage(
				new EmbedBuilder().setColor(color).setDescription(message).build()
		).complete();
		
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				msg.delete().queue();
			}
		}, timer);
	}
	
	public static void sendInformationMessage(MessageChannel channel, Color color, String message, Integer timer) {
		Message msg = channel.sendMessage(
				new EmbedBuilder().setColor(color).setDescription(message).build()
		).complete();
		
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				msg.delete().queue();
			}
		}, timer);
	}
	
	public static void sendInformationMessage(TextChannel channel, Color color, String message) {
		Message msg = channel.sendMessage(
				new EmbedBuilder().setColor(color).setDescription(message).build()
		).complete();
		
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				msg.delete().queue();
			}
		}, 5000);
	}
	
	public static void sendInformationMessage(MessageChannel channel, Color color, String message) {
		Message msg = channel.sendMessage(
				new EmbedBuilder().setColor(color).setDescription(message).build()
		).complete();
		
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				msg.delete().queue();
			}
		}, 5000);
	}
}
