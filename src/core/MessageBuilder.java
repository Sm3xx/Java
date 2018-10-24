package core;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import containers.MessageContainer;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import util.STATIC;

public class MessageBuilder {
	
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
	
	// 2 Languages with Title 
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
	
	// 1 Language without Title
	public static MessageEmbed buildEmbed(String title, String text, Color color, boolean footer) {
		
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(color);
		builder.addField(title, text.replaceAll("&prefix&", STATIC.PREFIX), false);
		if(footer) { 
			builder.setFooter("Bot developed by Veteran-Gaming", null);
		}
		
		return builder.build();
	}

	public static MessageEmbed buildTicket(String[] args, String author, String icon) {
		String request 	= "";
		for (int i=0; i<args.length; i++) {
			request = request + args[i] + " ";
		}
		
		String message = "User: **" + author + "**\n\nRequest: **" + request +"**";
		MessageEmbed msg = MessageBuilder.buildEmbed(Main.createTitle(icon, Main.getTimestamp()), message, null, false);
		return msg;
	}
	
	public static void sendAdminLog(String text, String emote, TextChannel channel) {
		String title = emote + " "+ Main.getTimestamp();
		channel.sendMessage(buildEmbed(title, text, null, false)).queue();
	}

	public static MessageContainer sendPrivateMessage(User user, MessageEmbed content, String messageType) {
		PrivateChannel channel = user.openPrivateChannel().complete();
		String id = channel.sendMessage(content).complete().getId();
		return new MessageContainer(channel, id, null);
	}

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
