package listeners;

import java.util.HashMap;

import commands.AutoChannel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;

public class VoiceChannelListener extends ListenerAdapter{
	
	private HashMap<VoiceChannel, Guild> createdChannels = new HashMap<>();
	private static String acTag = "[temp]";
	
	private static VoiceChannel getVoiceChan(Guild g, String id) {
		return g.getVoiceChannelById(id);
	}
	
	public static void setTag(String tag) {
		acTag = "["+tag+"]";
	}

	public void onGuildVoiceJoin(GuildVoiceJoinEvent event ) {
		GuildController controller = new GuildController(event.getGuild());
		HashMap<String, String> ac = AutoChannel.getAutoChan();
		VoiceChannel joinedChannel = event.getChannelJoined();
		String cid = joinedChannel.getId();
		
		if (ac.containsKey(cid)) {
			VoiceChannel vc = getVoiceChan(event.getGuild(), joinedChannel.createCopy().complete().getId());
			vc.getManager().setName(joinedChannel.getName()+" "+acTag).complete();
			
			controller.moveVoiceMember(event.getMember(), vc).complete();
			
			createdChannels.put(vc, event.getGuild());
		}
	}
	
	public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
		VoiceChannel vc = event.getChannelLeft();
		
		if (event.getChannelLeft().getMembers().isEmpty()) {
			if (createdChannels.containsKey(vc)) {
				vc.delete().complete();
				createdChannels.remove(vc);
			}
		}
	}
	
}
