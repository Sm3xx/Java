package listeners;

import java.awt.Color;

import core.ExceptionContainer;
import core.Main;
import core.embedBuilder;
import core.roleHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
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
		EmbedBuilder builder = new EmbedBuilder().setColor(Color.green).setFooter(STATIC.FOOTER, null);
		
		if (event.getReaction().getPrivateChannel() != null && !event.getUser().isBot()) {
			if (Main.checkNewMember(guild, Main.getGuildMember(event.getUser()))) {
			
				if (emote.equalsIgnoreCase(STATIC.FLAG_DE)) {
					String[] reactions = {STATIC.VG, STATIC.GTA};
					sendMessage(event.getUser(), builder.setDescription(MESSAGES.INTEREST).build(), reactions);
				}
				
				if (emote.equalsIgnoreCase(STATIC.FLAG_GB)) {
					String[] reactions = {STATIC.HELP_RED};
					sendMessage(event.getUser(), builder.setDescription(MESSAGES.CHANGE_NAME_EN).build(), reactions);
				}
				
				if (emote.equalsIgnoreCase(STATIC.GTA)) {
					// Add GTA-Roleplay role to User
					ExceptionContainer error = roleHandler.addRole(guild, member, ROLES.GTA_ROLEPLAY_GUEST);
					if (error == null) {
						Main.sendPrivateMessage(event.getUser(), embedBuilder.buildEmbed(MESSAGES.CONFIRMATION_TITLE_DE, MESSAGES.GTA_ROLE_ADDED, Color.GREEN, true), "Role-Add");
					}
				}
				
				if (emote.equalsIgnoreCase(STATIC.VG)) {
					String[] reactions = {STATIC.HELP_GRAY};
					sendMessage(event.getUser(), builder.setDescription(MESSAGES.CHANGE_NAME_DE).build(), reactions); 
				}
				
				if (emote.equalsIgnoreCase(STATIC.HELP_GRAY)) {
					sendMessage(event.getUser(), builder.setDescription(MESSAGES.NAMECHANGE_HELP_DE).build(), null);
				}
				
				if (emote.equalsIgnoreCase(STATIC.HELP_RED)) {
					sendMessage(event.getUser(), builder.setDescription(MESSAGES.NAMECHANGE_HELP_EN).build(), null);
				}
				
			}
		}
		
	}
	
	private void sendMessage(User user, MessageEmbed content, String[] reactions) {
		user.openPrivateChannel().queue((channel) ->
        {
    		String id = channel.sendMessage(content).complete().getId();
    		
    		if (reactions != null) {
	    		for (String reaction : reactions) {
	    			channel.addReactionById(id, reaction).queue();
	    		}
    		}
        });
	}
	
}
