package listeners;

import core.Main;
import core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.core.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.STATIC;

public class ChannelListener extends ListenerAdapter{

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
	
}
