package listeners;

import java.awt.Color;
import core.ErrorHandler;
import core.ExceptionContainer;
import core.Logger;
import core.Main;
import core.embedBuilder;
import core.roleHandler;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.MESSAGES;
import util.ROLES;
import util.STATIC;

public class nicknameChangeListener extends ListenerAdapter{

	public void onGuildMemberNickChange(GuildMemberNickChangeEvent event) {		
		TextChannel channel = Main.getChannel(STATIC.ADMIN_LOG, event.getGuild());
		String prevNick = event.getPrevNick();
		String newNick = event.getNewNick();
		
		if (prevNick == null) prevNick = event.getMember().getEffectiveName();
		if (newNick == null) newNick = event.getMember().getEffectiveName();
		
		String msg = "**" + prevNick + "**" + " changed his name to **" + newNick + "**";			
		
		channel.sendMessage(embedBuilder.buildEmbed(STATIC.INFO + " "+Main.getTimestamp(), msg, null, false)).queue();
		
		// Checking if user is a new User
		if (Main.checkNewMember(event.getGuild(), event.getMember())) {
			if (!newNick.contains(STATIC.TAG) && !newNick.contains(STATIC.TAG.toUpperCase())) {
				if (newNick.contains("|")) {
					String[] name = newNick.split(" ");
					if (name.length >= 3) {
						ExceptionContainer error = roleHandler.addRole(event.getGuild(), event.getMember(), ROLES.GUEST);
						if (error == null) {
							MessageEmbed message = embedBuilder.buildEmbed(Main.createTitle(STATIC.FLAG_DE, MESSAGES.GUEST_ROLE_TITLE_DE+" "+newNick), MESSAGES.GUEST_ROLE_ADDED_DE, Main.createTitle(STATIC.FLAG_GB, MESSAGES.GUEST_ROLE_TITLE_EN+" "+newNick), MESSAGES.GUEST_ROLE_ADDED_EN, Color.WHITE, true);
							Main.sendPrivateMessage(event.getUser(), message, "Guest-Role add");
						} else {
							Logger.error(error.getMessage());;
						}
					} else {
						// not enough args in the nickname
						ErrorHandler.sendErr(event.getUser(), MESSAGES.ERR_NAMECHANGE_DE, MESSAGES.ERR_NAMECHANGE_EN, "Guest-Role add");
					}
				} else {
					// Nickname does not contain "|"
					ErrorHandler.sendErr(event.getUser(), MESSAGES.ERR_NAMECHANGE_DE, MESSAGES.ERR_NAMECHANGE_EN, "Guest-Role add");
				}
			} else {
				// Nickname contains -VG-
				ErrorHandler.sendErr(event.getUser(), MESSAGES.REMOVE_TAG_DE, MESSAGES.REMOVE_TAG_EN, "Guest-Role add");
			}
		}
		
	}
	
}
