package core.handlers;

import java.util.List;

import core.Main;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import util.STATIC;

public class GuildHandler {
	
	static TextChannel adminlog, waiting;

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
	
	/**
	 * Check if given Member has any Roles on the Discord server (if the user is new on the Server) 
	 * @param guild
	 * @param member
	 * @return true/false
	 */
	public static boolean checkNewMember(Member member) {
		List<Role> serverRoles = getGuild().getRoles();
		List<Role> userRoles = member.getRoles();
		boolean oldUser = false;
		
		for (Role sr : serverRoles) {
			for (Role ur : userRoles) {
				if (ur == sr) {
					oldUser = true;
					break;
				}
			}
		}
		
		if (oldUser) return false;
		else return true;
	}
	
}
