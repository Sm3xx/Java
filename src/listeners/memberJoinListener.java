package listeners;

import java.awt.Color;
import core.Main;
import core.embedBuilder;
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
		
	}
	
}
