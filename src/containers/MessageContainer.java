package containers;

import net.dv8tion.jda.core.entities.PrivateChannel;

public class MessageContainer {
	
	PrivateChannel channel;
	String messageid;
	String[] reactions;
	
	public MessageContainer(PrivateChannel channel, String messageid, String[] reactions) {
		this.channel = channel;
		this.messageid = messageid;
		this.reactions = reactions;
	}

}
