package core;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import commands.devJoin;
import commands.ping;
import commands.prune;
import commands.rulesDE;
import commands.rulesEN;
import commands.test;
import commands.ticket;
import listeners.channelListener;
import listeners.memberJoinListener;
import listeners.messageListener;
import listeners.nicknameChangeListener;
import listeners.reactionAddListener;
import listeners.readyListener;
import listeners.roleChangeListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
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
		builder.setGame(Game.playing("v"+STATIC.VERSION));
		
		addListeners();
		addCommand();
		getTimestamp();
		
		try {
			builder.buildBlocking();
		} catch (Exception e) {}
				
		
	}

	public static String getTimestamp() {
		
		final DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy || HH:mm");
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

	public static void sendInformationMessage(TextChannel channel, Color color, String message, Integer timer) {
		Message msg = channel.sendMessage(
				new EmbedBuilder().setColor(color).setDescription(message).build()
		).complete();
		
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				msg.delete().queue();
			}
		}, timer);
	}
	
	public static void sendInformationMessage(MessageChannel channel, Color color, String message, Integer timer) {
		Message msg = channel.sendMessage(
				new EmbedBuilder().setColor(color).setDescription(message).build()
		).complete();
		
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				msg.delete().queue();
			}
		}, timer);
	}
	
	public static void addCommand() {
		CommandHandler.registerCommand(new String[] {"ping"}, new ping());
		CommandHandler.registerCommand(new String[] {"join"}, new devJoin());
		CommandHandler.registerCommand(new String[] {"test", "Test"}, new test());
		CommandHandler.registerCommand(new String[] {"prune", "Prune"}, new prune());
		CommandHandler.registerCommand(new String[] {"rules_en", "Rules_en", "Rules_EN", "Rules_en"}, new rulesEN());
		CommandHandler.registerCommand(new String[] {"rules_de", "Rules_de", "Rules_DE", "Rules_De"}, new rulesDE());
		CommandHandler.registerCommand(new String[] {"ticket", "Ticket", "ticketgta", "TicketGta", "Ticketgta"}, new ticket());
	}
	
	public static void addListeners() {
		builder.addEventListener(new readyListener());
		builder.addEventListener(new memberJoinListener());
		builder.addEventListener(new messageListener());
		builder.addEventListener(new nicknameChangeListener());
		builder.addEventListener(new roleChangeListener());
		builder.addEventListener(new channelListener());
		builder.addEventListener(new reactionAddListener());
	}


}
