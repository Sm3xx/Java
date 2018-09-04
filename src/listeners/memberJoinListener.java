package listeners;

import java.awt.Color;
import core.Main;
import core.embedBuilder;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.MESSAGES;
import util.STATIC;

public class memberJoinListener extends ListenerAdapter{

	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		
		event.getUser().openPrivateChannel().queue((channel) ->
        {
        	MessageEmbed content = new EmbedBuilder().setDescription(MESSAGES.CHOOSE_LANG).setTitle(MESSAGES.CHOOSE_LANG_TITLE).setColor(Color.green).setFooter(STATIC.FOOTER, null).build();
    		String id = channel.sendMessage(content).complete().getId();
    		
    		channel.addReactionById(id, STATIC.FLAG_DE).queue();
    		channel.addReactionById(id, STATIC.FLAG_GB).queue();
        });
		
		TextChannel adminlog = Main.getChannel(STATIC.ADMIN_LOG, event.getGuild());
		TextChannel waiting = Main.getChannel(STATIC.WAITING_ROOM, event.getGuild());
		
		MessageEmbed content = embedBuilder.buildEmbed("Willkommen "+Main.getUserName(event.getMember()), MESSAGES.WAITING_ROOM_DE, "Welcome "+Main.getUserName(event.getMember()), MESSAGES.WAITING_ROOM_EN, Color.WHITE, true);
		waiting.sendMessage(event.getUser().getAsMention()+" joined");
		waiting.sendMessage(content).queue();
		
		
		String msg = "**" + event.getMember().getEffectiveName() + "** joined!";
		adminlog.sendMessage(embedBuilder.buildEmbed(STATIC.NEW + " " + Main.getTimestamp(), msg, null, false)).queue();
		
	}
	
}
