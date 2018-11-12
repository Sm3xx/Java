package core.handlers;

import java.util.List;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

public class MemberHandler {
	/**
	 * Get the Nickname of the Member. If the member has no nickname the effective name will be returned.
	 * @param member
	 * @return String
	 */
	public static String getUserName(Member member) {
		if (member.getNickname() == null) {
			return member.getEffectiveName();
		} else {
			return member.getNickname();
		}
	}
	
	/**
	 * Check if given Member has any Roles on the Discord server (if the user is new on the Server) 
	 * @param guild
	 * @param member
	 * @return true/false
	 */
	public static boolean checkNewMember(Member member) {
		List<Role> serverRoles = GuildHandler.getGuild().getRoles();
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
