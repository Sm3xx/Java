package listeners;

import java.util.HashMap;

import core.Main;
import core.MessageBuilder;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.core.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;
import util.STATIC;

public class ChannelListener extends ListenerAdapter{
	
	private HashMap<String, Guild> createdChannels = new HashMap<>();

	public void onTextChannelDelete(TextChannelDeleteEvent event) {
		String channelName = event.getChannel().getName();
		ChannelType channelType = event.getChannel().getType();
		
		TextChannel channel = Main.getChannel(STATIC.ADMIN_LOG, event.getGuild());
		
		MessageBuilder.sendAdminLog("**" + channelType + "**-Channel deleted called **" + channelName + "**", STATIC.INFO, channel);
	}
	
	public void onTextChannelCreate(TextChannelCreateEvent event) {
		String channelName = event.getChannel().getName();
		ChannelType channelType = event.getChannel().getType();
		
		TextChannel channel = Main.getChannel(STATIC.ADMIN_LOG, event.getGuild());
		
		MessageBuilder.sendAdminLog("**" + channelType + "**-Channel created called **" + channelName + "**", STATIC.INFO, channel);
	}
	
	public void onGuildVoiceJoin(GuildVoiceJoinEvent event ) {
		GuildController controller = new GuildController(event.getGuild());
		HashMap<String, Guild> ac = STATIC.autochannels;
		VoiceChannel joinedChannel = event.getChannelJoined();
		String cid = joinedChannel.getId();
		
		if (ac.containsKey(cid)) {
			Category cat = joinedChannel.getParent();
			String ncid = cat.createVoiceChannel(joinedChannel.getName()+ " [AC]").complete().getId();
			controller.moveVoiceMember(event.getMember(), event.getGuild().getVoiceChannelById(ncid)).complete();
			
			createdChannels.put(ncid, event.getGuild());
		}
	}
	
	public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
		String cid = event.getChannelLeft().getId();
		
		if (event.getChannelLeft().getMembers().isEmpty()) {
			if (createdChannels.containsKey(cid)) {
				event.getGuild().getVoiceChannelById(cid).delete().complete();
				createdChannels.remove(cid);
			}
		}
	}
	
}
