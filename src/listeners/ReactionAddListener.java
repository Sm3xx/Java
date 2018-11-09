package listeners;

import java.awt.Color;

import containers.ExceptionContainer;
import core.Logger;
import core.MessageBuilder;
import core.handlers.GuildHandler;
import core.handlers.RoleHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.MESSAGES;
import util.ROLES;
import util.RULES;
import util.STATIC;

public class ReactionAddListener extends ListenerAdapter{

	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		
		String emote = event.getReaction().getReactionEmote().getName();
		Guild guild = GuildHandler.getGuild();
		Member member = GuildHandler.getGuildMember(event.getUser());
		EmbedBuilder builder = new EmbedBuilder().setColor(STATIC.EMBED_COLOR).setFooter(STATIC.FOOTER, null);
		
		// registration Process
		if (event.getReaction().getPrivateChannel() != null && !event.getUser().isBot()) {
			if (GuildHandler.checkNewMember(member)) {
				
				// choose interest
				if (emote.equalsIgnoreCase(STATIC.FLAG_DE)) {
					MessageBuilder.sendReactionMessage(event.getUser(), builder.setDescription(MESSAGES.INTEREST).build(), new String[] {STATIC.VG, STATIC.GTA});
				}
				
				// join VG-Community - German
				if (emote.equalsIgnoreCase(STATIC.VG)) {
					MessageBuilder.sendReactionMessage(event.getUser(), builder.setDescription(MESSAGES.CHANGE_NAME_DE).build(), new String[] {STATIC.HELP_GRAY}); 
				}
				
				// join VG-Community - English
				if (emote.equalsIgnoreCase(STATIC.FLAG_GB)) {
					MessageBuilder.sendReactionMessage(event.getUser(), builder.setDescription(MESSAGES.CHANGE_NAME_EN).build(), new String [] {STATIC.HELP_RED});
				}
				
				// help - German
				if (emote.equalsIgnoreCase(STATIC.HELP_GRAY)) {
					MessageBuilder.sendReactionMessage(event.getUser(), builder.setDescription(MESSAGES.NAMECHANGE_HELP_DE).build(), null);
				}
				
				// help - English
				if (emote.equalsIgnoreCase(STATIC.HELP_RED)) {
					MessageBuilder.sendReactionMessage(event.getUser(), builder.setDescription(MESSAGES.NAMECHANGE_HELP_EN).build(), null);
				}
				
				// Add GTA-Role
				if (emote.equalsIgnoreCase(STATIC.GTA)) {
					ExceptionContainer error = RoleHandler.addRole(guild, member, ROLES.GTA_ROLEPLAY_GUEST);
					if (error == null) {
						MessageBuilder.sendPrivateMessage(event.getUser(), MessageBuilder.buildEmbed(MESSAGES.CONFIRMATION_TITLE_DE, MESSAGES.GTA_ROLE_ADDED, STATIC.EMBED_COLOR, true), "Role-Add");
					} else {
						MessageBuilder.sendPrivateMessage(event.getUser(),  new EmbedBuilder().setDescription(MESSAGES.ROLE_ADD_ERROR).setColor(Color.RED).build(), "Error");
						Logger.error("ReactionAddListener "+error.getMessage());
						MessageBuilder.sendAdminLog("ReactionAddListener "+error.getMessage(), STATIC.BANGBANG);
					}
				}
				
				
			}
			
			// German Rules
			if (emote.equalsIgnoreCase(STATIC.BOOK_BLUE)) {
					MessageBuilder.sendPrivateMessage(event.getUser(), MessageBuilder.buildEmbed(RULES.DE_TITLE, RULES.DE, STATIC.EMBED_COLOR, true), "Rules DE");
			}
			
			// English Rules
			if (emote.equalsIgnoreCase(STATIC.BOOK_RED)) {
					MessageBuilder.sendPrivateMessage(event.getUser(), MessageBuilder.buildEmbed(RULES.EN_TITLE, RULES.EN, STATIC.EMBED_COLOR, true), "Rules DE");
			}
		}
		
	}
	
}
