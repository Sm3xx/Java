package listeners;

import java.awt.Color;
import core.Main;
import core.embedBuilder;
import core.roleHandler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.MESSAGES;
import util.ROLES;
import util.STATIC;

public class reactionAddListener extends ListenerAdapter{

	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		
		String emote = event.getReaction().getReactionEmote().getName();
		Guild guild = Main.getGuild(event.getUser());
		Member member = guild.getMemberById(event.getUser().getId());
		
		if (event.getReaction().getPrivateChannel() != null && !event.getUser().isBot()) {
			if (Main.checkNewMember(guild, Main.getGuildMember(event.getUser()))) {
			
				if (emote.equalsIgnoreCase(STATIC.CONTROLLER)) {
					// Add GTA-Roleplay role to User
					roleHandler.addRole(guild, member, ROLES.GTA_ROLEPLAY_GUEST);
					Main.sendPrivateMessage(event.getUser(), embedBuilder.buildEmbed(MESSAGES.CONFIRMATION_TITLE_DE, MESSAGES.GTA_ROLE_ADDED, Color.GREEN, true), "Role-Add");						
					
				}
				
				if (emote.equalsIgnoreCase(STATIC.FLAG_DE)) {
					// Send Extended help message
					Main.sendPrivateMessage(event.getUser(), embedBuilder.buildEmbed(MESSAGES.NAMECHANGE_HELP_TITLE_DE, MESSAGES.NAMECHANGE_HELP_DE, null, true), "Help");
				}
				
				if (emote.equalsIgnoreCase(STATIC.FLAG_GB)) {
					// Send Extended help message 
					Main.sendPrivateMessage(event.getUser(), embedBuilder.buildEmbed(MESSAGES.NAMECHANGE_HELP_TITLE_EN, MESSAGES.NAMECHANGE_HELP_EN, null, true), "Help");
				}
			}
		}
		
	}
	
}
