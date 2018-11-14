package core.handlers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import containers.ExceptionContainer;
import core.Reader;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import util.ROLES;

public class RoleHandler {
	
	private static String createString(String key, String rn) {
		return "**"+key+"**: "+rn;
	}
	
	private static void setName(String key, String value) {
		switch (key) {
		case "admin": 
			ROLES.ADMIN = value;
			break;
		case "moderator":
			ROLES.MODERATOR = value;
			break;
		case "member":
			ROLES.MEMBER = value;
			break;
		case "trialmember":
			ROLES.TRIALMEMBER = value;
			break;
		case "guest":
			ROLES.GUEST = value;
			break;
		case "gta-guest":
			ROLES.GTA_ROLEPLAY_GUEST = value;
			break;
		case "developer":
			ROLES.DEV = value;
			break;
		}
	}
	
	private static ExceptionContainer setRoleNames() {
		try {
			List<String> content = Reader.readFile("./config/roles.txt");
			
			if (content == null) 
				return new ExceptionContainer(null, "File \"roles.txt\" could not be loaded!");
			
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

	public static void printRolenames(TextChannel tc) {
		String msg = "";
		String[] msgArray = { 
				createString("Admin", ROLES.ADMIN),
				createString("Moderator", ROLES.MODERATOR),
				createString("Member", ROLES.MEMBER),
				createString("Trialmember", ROLES.TRIALMEMBER),
				createString("Guest", ROLES.GUEST),
				createString("GTA-Guest", ROLES.GTA_ROLEPLAY_GUEST),
				createString("Developer", ROLES.DEV)
		};
		
		for (int i=0; i<msgArray.length; i++) {
			msg = msg + msgArray[i] + "\n";
		}
		
		tc.sendMessage(new EmbedBuilder().setColor(Color.green).setDescription(msg).setTitle("Rolenames:").build()).queue();
	}
	
	public static ExceptionContainer loadRoleNames() {
		return setRoleNames();
	}
	
	public static Role getRole(Guild guild, String rolename) {
		List<Role> roleList = guild.getRolesByName(rolename, true);
		if (!roleList.isEmpty()) {
			return roleList.get(0);
		}
		
		return null;		
	}

	public static ExceptionContainer addRole(Guild guild, Member member, Role role) {
		try {
			guild.getController().addSingleRoleToMember(member, role).complete();
			return null;
		} catch (Exception e) {
			return new ExceptionContainer(e, "addRole - " +e.getMessage());
		}
	}
	
	public static ExceptionContainer bulkAddRole(Guild guild, List<Member> members, Role role) {
		try {
			for (Member member : members) {
				if (!member.getUser().isBot()) {
					ExceptionContainer error = addRole(guild, member, role);
					if (error != null) {
						return error;
					}
				}
			}
			return null;
		} catch (Exception e) {
			return new ExceptionContainer(e, "bulkAddRole - " + e.getMessage());
		}
	}
	
	public static ExceptionContainer bulkRemoveRole(Guild guild, List<Member> members, String rolename) {
		try {
			List<Role> roleList = new ArrayList<Role>();
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
