package core;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import util.STATIC;

public class embedBuilder {
	
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
		MessageEmbed msg = embedBuilder.buildEmbed(Main.createTitle(icon, Main.getTimestamp()), message, null, false);
		return msg;
	}
	
	public static void sendAdminLog(String text, String emoji, TextChannel channel) {
		String title = emoji + " "+ Main.getTimestamp();
		channel.sendMessage(buildEmbed(title, text, null, false)).queue();
	}

}
