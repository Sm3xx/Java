package core.handlers;

import java.util.HashMap;
import java.util.List;

import containers.ExceptionContainer;
import core.Main;
import core.Reader;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import util.STATIC;

public class GuildHandler {
	
	static TextChannel adminlog, waiting;
	
	private static void setName(String key, String value) {
		
		switch (key) {
			case "admin": 
				STATIC.ADMIN_LOG = value;
				break;
				
			case "error":
				STATIC.ERROR_LOG = value;
				break;
				
			case "waiting":
				STATIC.WAITING_ROOM = value;
				break;
				
			case "ticket":
				STATIC.ADMIN_TICKETS = value;
				break;
				
			case "gta-ticket-bot":
				if (value.equalsIgnoreCase("true")) {
					STATIC.ACTIVE_TICKET_BOT = true;
				} else {
					STATIC.ACTIVE_TICKET_BOT = false;
				}
				break;
				
			case "gta-ticket":
				STATIC.GTA_TICKETS = value;
				break;
				
			case "gta-ticket-dump":
				STATIC.GTA_TICKETS_DUMP = value;
				break;
		}
	}
	
	private static ExceptionContainer setChannelNames() {
		try {
			List<String> content = Reader.readFile("./config/channels.txt");
			
			if (content == null) 
				return new ExceptionContainer(null, "File \"channels.txt\" could not be loaded!");
			
			for (int i=0; i<content.size(); i++) {
				HashMap<String, String> pairs = Reader.createKeyValuePair(content);
				
				for (String key : pairs.keySet()) {
					String value = pairs.get(key);
					setName(key, value);
				}
				
			}
			return null;
		} catch (Exception e) {
			return new ExceptionContainer(e, e.getMessage());
		}
	}

	public static ExceptionContainer loadChannelNames() {
		return setChannelNames();
	}
	
	public static TextChannel getAdminlog() {
		if (adminlog == null && STATIC.ADMIN_LOG != "") {
			adminlog = getChannel(STATIC.ADMIN_LOG);
		}
		return adminlog;
	}
	
	public static TextChannel getWaiting() {
		if (waiting == null && STATIC.WAITING_ROOM != "") {
			waiting = getChannel(STATIC.WAITING_ROOM);
		}
		return waiting;
	}

	/**
	 * Returns the Guild
	 * @return guild
	 */
	
	public static Guild getGuild() {
		return Main.getJDA().getGuilds().get(0);
	}
	
	/**
	 * get a member of a guild
	 * @param user
	 * @return Member
	 */
	public static Member getGuildMember(User user) {
		return getGuild().getMemberById(user.getId());
	}
	
	/**
	 * Get the TextChannel of the guild specified in the channelId
	 * @param channelId
	 * @param guild
	 * @return TextChannel
	 */
	public static TextChannel getChannel(String channelId) {
		try {
			List<TextChannel> channelList = getGuild().getTextChannelsByName(channelId, true);
			TextChannel channel = channelList.get(0);
			
			return channel;
		} catch (Exception e) {
			return null;
		}
	}
	
}
