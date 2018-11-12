package listeners;

import java.awt.Color;

import containers.ExceptionContainer;
import core.Logger;
import core.MessageBuilder;
import core.handlers.GuildHandler;
import core.handlers.MemberHandler;
import core.handlers.RoleHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.EMOTES;
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
			if (MemberHandler.checkNewMember(member)) {
				
				// choose interest
				if (emote.equalsIgnoreCase(EMOTES.FLAG_DE)) {
					MessageBuilder.sendReactionMessage(event.getUser(), builder.setDescription(MESSAGES.INTEREST).build(), new String[] {EMOTES.VG, EMOTES.GTA});
				}
				
				// join VG-Community - German
				if (emote.equalsIgnoreCase(EMOTES.VG)) {
					MessageBuilder.sendReactionMessage(event.getUser(), builder.setDescription(MESSAGES.CHANGE_NAME_DE).build(), new String[] {EMOTES.HELP_GRAY}); 
				}
				
				// join VG-Community - English
				if (emote.equalsIgnoreCase(EMOTES.FLAG_GB)) {
					MessageBuilder.sendReactionMessage(event.getUser(), builder.setDescription(MESSAGES.CHANGE_NAME_EN).build(), new String [] {EMOTES.HELP_RED});
				}
				
				// help - German
				if (emote.equalsIgnoreCase(EMOTES.HELP_GRAY)) {
					MessageBuilder.sendReactionMessage(event.getUser(), builder.setDescription(MESSAGES.NAMECHANGE_HELP_DE).build(), null);
				}
				
				// help - English
				if (emote.equalsIgnoreCase(EMOTES.HELP_RED)) {
					MessageBuilder.sendReactionMessage(event.getUser(), builder.setDescription(MESSAGES.NAMECHANGE_HELP_EN).build(), null);
				}
				
				// Add GTA-Role
				if (emote.equalsIgnoreCase(EMOTES.GTA)) {
					ExceptionContainer error = RoleHandler.addRole(guild, member, RoleHandler.getRole(guild, ROLES.GTA_ROLEPLAY_GUEST));
					if (error == null) {
						MessageBuilder.sendPrivateMessage(event.getUser(), MessageBuilder.buildEmbed(MESSAGES.CONFIRMATION_TITLE_DE, MESSAGES.GTA_ROLE_ADDED, STATIC.EMBED_COLOR, true), "Role-Add");
					} else {
						MessageBuilder.sendPrivateMessage(event.getUser(),  new EmbedBuilder().setDescription(MESSAGES.ROLE_ADD_ERROR).setColor(Color.RED).build(), "Error");
						Logger.error("ReactionAddListener "+error.getMessage());
						MessageBuilder.sendAdminLog("ReactionAddListener "+error.getMessage(), EMOTES.BANGBANG);
					}
				}
				
				
			}
			
			// German Rules
			if (emote.equalsIgnoreCase(EMOTES.BOOK_BLUE)) {
					MessageBuilder.sendPrivateMessage(event.getUser(), MessageBuilder.buildEmbed(RULES.DE_TITLE, RULES.DE, STATIC.EMBED_COLOR, true), "Rules DE");
			}
			
			// English Rules
			if (emote.equalsIgnoreCase(EMOTES.BOOK_RED)) {
					MessageBuilder.sendPrivateMessage(event.getUser(), MessageBuilder.buildEmbed(RULES.EN_TITLE, RULES.EN, STATIC.EMBED_COLOR, true), "Rules EN");
			}
		}
		
	}
	
}
