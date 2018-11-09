package listeners;

import java.awt.Color;
import java.util.List;

import core.Logger;
import core.MessageBuilder;
import core.handlers.MemberHandler;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.MESSAGES;
import util.ROLES;
import util.STATIC;

public class RoleChangeListener extends ListenerAdapter{
	
	// User gets new Role
	public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
		List<Role> roles = event.getRoles();
		Member m = event.getMember();
		
		checkRolename(roles.get(0).getName(), event);
		
		String msg = createMessage(roles.get(0).getName(), getName(m), false);
		MessageBuilder.sendAdminLog(msg, STATIC.CHECKMARK);
		Logger.info(roles.get(0).getName()+"-Role added to "+getName(m));
	}
	
	// Role removed from user
    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
    	List<Role> roles = event.getRoles();
		String name = MemberHandler.getUserName(event.getMember());
		
		String msg = createMessage(roles.get(0).getName() , name, true);
		MessageBuilder.sendAdminLog(msg, STATIC.CROSS);
		Logger.info(roles.get(0).getName()+"-Role removed from "+name);
    }
    
    private String getName(Member m) {
    	return MemberHandler.getUserName(m);
    }
    
    // Create the message String for the Admin Log
    private String createMessage(String rolename, String username, boolean removed) {
    	if (removed) return "**" + rolename + "**-Role removed from **" + username + "**"; 
    	else return "**" + rolename + "**-Role assigned to **" + username + "**";
    }
    
    private void sendWelcomeMessage(MessageEmbed content, GuildMemberRoleAddEvent event) {
    	MessageBuilder.sendPrivateMessage(event.getUser(), content, "Community-Join");
    }
    
    private void checkRolename(String rolename, GuildMemberRoleAddEvent event) {
    	MessageEmbed message = null;
    	String title_de = STATIC.PARTY+" "+MESSAGES.JOIN_TITLE_DE+" "+STATIC.PARTY;
    	String title_en = STATIC.PARTY+" "+MESSAGES.JOIN_TITLE_EN+" "+STATIC.PARTY;
    	
    	if (rolename == ROLES.MEMBER) {
			message = MessageBuilder.buildEmbed(title_de, MESSAGES.MEMBER_JOIN_DE, title_en, MESSAGES.MEMBER_JOIN_EN, Color.WHITE, true);
			sendWelcomeMessage(message, event);
    	}
    	
    	if (rolename == ROLES.TRIALMEMBER) {
			message = MessageBuilder.buildEmbed(title_de, MESSAGES.TRIAL_JOIN_DE, title_en, MESSAGES.TRIAL_JOIN_EN, Color.WHITE, true);
			sendWelcomeMessage(message, event);
    	}
    }
	
}
