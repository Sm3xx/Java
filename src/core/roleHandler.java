package core;

import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

public class roleHandler {
	
	public static Role getRole(Guild guild, String rolename) {
		List<Role> roleList = guild.getRolesByName(rolename, true);
		if (!roleList.isEmpty()) {
			return roleList.get(0);
		}
		
		return null;		
	}

	public static ExceptionContainer addRole(Guild guild, Member member, String rolename) {
		try {
			Role role = getRole(guild, rolename);
			guild.getController().addSingleRoleToMember(member, role).complete();
			return null;
		} catch (Exception e) {
			return new ExceptionContainer(e, "addRole - " +e.getMessage());
		}
	}
	
	public static ExceptionContainer bulkAddRole(Guild guild, List<Member> members, String rolename) {
		try {
			for (Member member : members) {
				ExceptionContainer error = addRole(guild, member, rolename);
				if (error != null) {
					return error;
				}
			}
			return null;
		} catch (Exception e) {
			return new ExceptionContainer(e, "bulkAddRole - " + e.getMessage());
		}
	}
	
	@SuppressWarnings("null")
	public static ExceptionContainer bulkRemoveRole(Guild guild, List<Member> members, String rolename) {
		try {
			List<Role> roleList = null;
			Role role = getRole(guild, rolename);
			roleList.add(role);
			for (Member member : members) {
				member.getRoles().removeAll(roleList);
			}
			return null;
		} catch (Exception e) {
			return new ExceptionContainer(e, "bulkRemoveRole - " + e.getMessage());
		}
	}
	
}
