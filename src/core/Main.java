package core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import commands.devJoin;
import commands.ping;
import commands.prune;
import commands.rulesDE;
import commands.rulesEN;
import commands.ticket;
import listeners.channelListener;
import listeners.commandListener;
import listeners.nicknameChangeListener;
import listeners.reactionAddListener;
import listeners.readyListener;
import listeners.roleChangeListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import util.SECRETS;
import util.STATIC;

public class Main {
	
	static JDABuilder builder = new JDABuilder (AccountType.BOT);
	
	public static void main(String[] Args) {
		
		builder.setToken(SECRETS.TOKEN);
		builder.setAutoReconnect(true);
		
		addListeners();
		addCommand();
		getTimestamp();
		
		try {
			@SuppressWarnings("unused")
			JDA jda = builder.buildBlocking();
		} catch (Exception e) {}
				
		
	}

	public static String getTimestamp() {
		
		final DateFormat sdf = new SimpleDateFormat("dd.MM.yyy || HH:mm");
		Date date = new Date();

		return sdf.format(date);
	}

	public static String getUserName(Member member) {
		if (member.getNickname() == null) {
			return member.getEffectiveName();
		} else {
			return member.getNickname();
		}
	}

	public static String createTitle(String emote, String titleContent) {
	
	
		String title = emote + " " + titleContent;
		
		return title;
	}
	
	public static TextChannel getChannel(String channelId, Guild guild) {
		List<TextChannel> channelList = guild.getTextChannelsByName(channelId, true);
		TextChannel channel = channelList.get(0);
		
		return channel;
	}
	
	public static Guild getGuild(User user) {
		Guild guild = null;
		List<Guild> guilds = user.getMutualGuilds();
		for (Guild g : guilds) {
			if (g.getId().equalsIgnoreCase(STATIC.SERVERID)) {
				guild = g;
				break;
			}
		}
		return guild;
	}
	
	public static Member getGuildMember(User user) {
		return getGuild(user).getMemberById(user.getId());
	}

	public static boolean checkNewMember(Guild guild, Member member) {
		
		List<Role> serverRoles = guild.getRoles();
		List<Role> userRoles = member.getRoles();
		boolean oldUser = false;
		
		
		for (Role sr : serverRoles) {
			for (Role ur : userRoles) {
				if (ur == sr) {
					System.out.println(ur.getName() + " - " + sr.getName());
					oldUser = true;
					break;
				}
			}
		}
		
		if (oldUser) return false;
		else return true;
	}
	
	public static void sendPrivateMessage(User user, MessageEmbed content, String messageType) {
		user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(content).complete();
        });
	}

	public static void addCommand() {
		CommandHandler.commands.put("ping", new ping());
		CommandHandler.commands.put("ticket", new ticket());
		CommandHandler.commands.put("rules_en", new rulesEN());
		CommandHandler.commands.put("rules_de", new rulesDE());
		CommandHandler.commands.put("prune", new prune());
		CommandHandler.commands.put("join", new devJoin());
	}
	
	public static void addListeners() {
		builder.addEventListener(new readyListener());
		builder.addEventListener(new commandListener());
		builder.addEventListener(new nicknameChangeListener());
		builder.addEventListener(new roleChangeListener());
		builder.addEventListener(new channelListener());
		builder.addEventListener(new reactionAddListener());
	}


}
