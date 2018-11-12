package listeners;

import core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.core.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.EMOTES;

public class ChannelChangeListener extends ListenerAdapter{
	
	public void onTextChannelDelete(TextChannelDeleteEvent event) {
		String channelName = event.getChannel().getName();
		ChannelType channelType = event.getChannel().getType();
		
		
		MessageBuilder.sendAdminLog("**" + channelType + "**-Channel deleted called **" + channelName + "**", EMOTES.INFO);
	}
	
	public void onTextChannelCreate(TextChannelCreateEvent event) {
		String channelName = event.getChannel().getName();
		ChannelType channelType = event.getChannel().getType();
		
		
		MessageBuilder.sendAdminLog("**" + channelType + "**-Channel created called **" + channelName + "**", EMOTES.INFO);
	}
	
}
