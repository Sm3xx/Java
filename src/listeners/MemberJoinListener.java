package listeners;

import java.awt.Color;

import core.Main;
import core.MessageBuilder;
import core.handlers.GuildHandler;
import core.handlers.MemberHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.EMOTES;
import util.MESSAGES;
import util.STATIC;

public class MemberJoinListener extends ListenerAdapter{

	static TextChannel adminlog, waiting;
	
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		execJoinEvent(event.getMember(), event.getGuild());
	}
	
	public static void execJoinEvent(Member m, Guild g) {
		MessageEmbed content;
		
		adminlog = GuildHandler.getAdminlog();
		waiting = GuildHandler.getWaiting();
		
		content = new EmbedBuilder().setDescription(MESSAGES.CHOOSE_LANG).setTitle(MESSAGES.CHOOSE_LANG_TITLE).setColor(STATIC.EMBED_COLOR).setFooter(STATIC.FOOTER, null).build();		
		MessageBuilder.sendReactionMessage(m.getUser(), content, new String[] {EMOTES.FLAG_DE, EMOTES.FLAG_GB});
		
		if (waiting != null) {
			String username = MemberHandler.getUserName(m);
			content = MessageBuilder.buildEmbed("Willkommen "+username, MESSAGES.WAITING_ROOM_DE, "Welcome "+username, MESSAGES.WAITING_ROOM_EN, Color.WHITE, true);
			
			waiting.sendMessage(m.getUser().getAsMention()+" joined").queue();
			waiting.sendMessage(content).queue();
		}
			
		String msg = "**" + m.getEffectiveName() + "** joined!";
		adminlog.sendMessage(MessageBuilder.buildEmbed(EMOTES.NEW + " " + Main.getTimestamp(), msg, null, false)).queue();
	}
	
}
