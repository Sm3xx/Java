package core.handlers;

import net.dv8tion.jda.core.entities.Member;

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
}
