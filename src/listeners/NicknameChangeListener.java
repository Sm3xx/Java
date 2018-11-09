package listeners;

import java.awt.Color;

import containers.ExceptionContainer;
import core.Logger;
import core.Main;
import core.MessageBuilder;
import core.handlers.GuildHandler;
import core.handlers.RoleHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.MESSAGES;
import util.ROLES;
import util.STATIC;

public class NicknameChangeListener extends ListenerAdapter{
	
	private static void sendErr(User user, String message_de, String message_en, String errorType) {
		String title_de = MessageBuilder.createTitle(STATIC.FLAG_DE, MESSAGES.ERROR_TITLE_DE);
		String title_en = MessageBuilder.createTitle(STATIC.FLAG_GB, MESSAGES.ERROR_TITLE_EN);
		MessageBuilder.sendPrivateMessage(user, MessageBuilder.buildEmbed(title_de , message_de, title_en, message_en, Color.RED, true), errorType);
	}

	public void onGuildMemberNickChange(GuildMemberNickChangeEvent event) {		
		TextChannel channel = GuildHandler.getAdminlog();
		String prevNick = event.getPrevNick();
		String newNick = event.getNewNick();
		
		if (prevNick == null) prevNick = event.getMember().getEffectiveName();
		if (newNick == null) newNick = event.getMember().getEffectiveName();
		
		String msg = "**" + prevNick + "**" + " changed his name to **" + newNick + "**";			
		
		channel.sendMessage(MessageBuilder.buildEmbed(STATIC.INFO + " "+Main.getTimestamp(), msg, null, false)).queue();
		
		// Checking if user is a new User
		if (GuildHandler.checkNewMember(event.getMember())) {
			if (!newNick.contains(STATIC.TAG) && !newNick.contains(STATIC.TAG.toUpperCase())) {
				if (newNick.contains("|")) {
					String[] name = newNick.split(" ");
					if (name.length >= 3) {
						ExceptionContainer error = RoleHandler.addRole(event.getGuild(), event.getMember(), ROLES.GUEST);
						if (error == null) {
							MessageEmbed message = MessageBuilder.buildEmbed(MESSAGES.GUEST_ROLE_TITLE_DE+" "+newNick, MESSAGES.GUEST_ROLE_ADDED_DE, MESSAGES.GUEST_ROLE_TITLE_EN+" "+newNick, MESSAGES.GUEST_ROLE_ADDED_EN, STATIC.EMBED_COLOR, true);
							MessageBuilder.sendReactionMessage(event.getUser(), message, new String[] {STATIC.BOOK_BLUE, STATIC.BOOK_RED});
						} else {
							MessageBuilder.sendPrivateMessage(event.getUser(),  new EmbedBuilder().setDescription(MESSAGES.ROLE_ADD_ERROR).setColor(Color.RED).build(), "Error");
							Logger.error(error.getMessage());
							MessageBuilder.sendAdminLog("NicknameChangeListener "+error.getMessage(), STATIC.BANGBANG);
						}
					} else {
						// not enough args in the nickname
						sendErr(event.getUser(), MESSAGES.ERR_NAMECHANGE_DE, MESSAGES.ERR_NAMECHANGE_EN, "Guest-Role add");
					}
				} else {
					// Nickname does not contain "|"
					sendErr(event.getUser(), MESSAGES.ERR_NAMECHANGE_DE, MESSAGES.ERR_NAMECHANGE_EN, "Guest-Role add");
				}
			} else {
				// Nickname contains -VG-
				sendErr(event.getUser(), MESSAGES.REMOVE_TAG_DE, MESSAGES.REMOVE_TAG_EN, "Guest-Role add");
			}
		}
		
	}
	
}
