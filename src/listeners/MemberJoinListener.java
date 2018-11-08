package listeners;

import java.awt.Color;

import core.Main;
import core.MessageBuilder;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.MESSAGES;
import util.STATIC;

public class MemberJoinListener extends ListenerAdapter{

	public void onGuildMemberJoin(GuildMemberJoinEvent event) {	
//		MessageEmbed content;
//		TextChannel adminlog, waiting;
//		
//		adminlog = Main.getChannel(STATIC.ADMIN_LOG, event.getGuild());
//		waiting = Main.getChannel(STATIC.WAITING_ROOM, event.getGuild());
//		
//		content = new EmbedBuilder().setDescription(MESSAGES.CHOOSE_LANG).setTitle(MESSAGES.CHOOSE_LANG_TITLE).setColor(STATIC.EMBED_COLOR).setFooter(STATIC.FOOTER, null).build();		
//		MessageBuilder.sendReactionMessage(event.getUser(), content, new String[] {STATIC.FLAG_DE, STATIC.FLAG_GB});
//		
//		String username = Main.getUserName(event.getMember());
//		content = MessageBuilder.buildEmbed("Willkommen "+username, MESSAGES.WAITING_ROOM_DE, "Welcome "+username, MESSAGES.WAITING_ROOM_EN, Color.WHITE, true);
//		
//		waiting.sendMessage(event.getUser().getAsMention()+" joined").queue();
//		waiting.sendMessage(content).queue();
//		
//		String msg = "**" + event.getMember().getEffectiveName() + "** joined!";
//		adminlog.sendMessage(MessageBuilder.buildEmbed(STATIC.NEW + " " + Main.getTimestamp(), msg, null, false)).queue();
		execJoinEvent(event.getMember(), event.getGuild());
	}
	
	public static void execJoinEvent(Member m, Guild g) {
		MessageEmbed content;
		TextChannel adminlog, waiting;
		
		adminlog = Main.getChannel(STATIC.ADMIN_LOG, g);
		waiting = Main.getChannel(STATIC.WAITING_ROOM, g);
		
		content = new EmbedBuilder().setDescription(MESSAGES.CHOOSE_LANG).setTitle(MESSAGES.CHOOSE_LANG_TITLE).setColor(STATIC.EMBED_COLOR).setFooter(STATIC.FOOTER, null).build();		
		MessageBuilder.sendReactionMessage(m.getUser(), content, new String[] {STATIC.FLAG_DE, STATIC.FLAG_GB});
		
		String username = Main.getUserName(m);
		content = MessageBuilder.buildEmbed("Willkommen "+username, MESSAGES.WAITING_ROOM_DE, "Welcome "+username, MESSAGES.WAITING_ROOM_EN, Color.WHITE, true);
		
		waiting.sendMessage(m.getUser().getAsMention()+" joined").queue();
		waiting.sendMessage(content).queue();
		
		String msg = "**" + m.getEffectiveName() + "** joined!";
		adminlog.sendMessage(MessageBuilder.buildEmbed(STATIC.NEW + " " + Main.getTimestamp(), msg, null, false)).queue();
	}
	
}
