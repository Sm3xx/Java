package core;

import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

public class roleHandler {

	public static void addRole(Guild guild, Member member, String rolename) {
			List<Role> roleList = guild.getRolesByName(rolename, true);
			Role role = roleList.get(0);
			guild.getController().addSingleRoleToMember(member, role).queue();
	}
	
}
