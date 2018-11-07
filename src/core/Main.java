package core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import commands.AddServerRole;
import commands.AutoChannel;
import commands.DevJoin;
import commands.Ping;
import commands.Prune;
import commands.Rules_de;
import commands.Rules_en;
import commands.Ticket;
import listeners.ChannelListener;
import listeners.MemberJoinListener;
import listeners.MessageListener;
import listeners.NicknameChangeListener;
import listeners.ReactionAddListener;
import listeners.ReadyListener;
import listeners.RoleChangeListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import util.SECRETS;
import util.STATIC;
import util.COMMANDS;

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
	
	/**
	 * Returns a Timestamp
	 * @return String
	 */
	public static String getTimestamp() {
		
		final DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy || HH:mm");
		Date date = new Date();

		return sdf.format(date);
	}
	
	/**
	 * Get the Nickname of the Member. If the member has no nickname the effective name will be returned.
	 * @param member
	 * @return String
	 */
	public static String getUserName(Member member) {
		if (member.getNickname() == null) {
			return member.getEffectiveName();
		} else {
			return member.getNickname();
		}
	}
	
	/**
	 * Creates string for the Title field of an embeded Message
	 * @param emote
	 * @param titleContent
	 * @return String
	 */
	public static String createTitle(String emote, String titleContent) {	
		String title = emote + " " + titleContent;
		return title;
	}
	
	/**
	 * Get the TextChannel of the guild specified in the channelId
	 * @param channelId
	 * @param guild
	 * @return TextChannel
	 */
	public static TextChannel getChannel(String channelId, Guild guild) {
		List<TextChannel> channelList = guild.getTextChannelsByName(channelId, true);
		TextChannel channel = channelList.get(0);
		
		return channel;
	}
	
	/**
	 * Returns the Guild
	 * @param user
	 * @return guild
	 */
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
	
	/**
	 * get a member of a guild
	 * @param user
	 * @return Member
	 */
	public static Member getGuildMember(User user) {
		return getGuild(user).getMemberById(user.getId());
	}

	/**
	 * Check if given Member has any Roles on the Discord server (if the user is new on the Server) 
	 * @param guild
	 * @param member
	 * @return true/false
	 */
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
	
	/**
	 * Check if the given Member has the permission to execute the following code
	 * @param member
	 * @param permissions
	 * @return true/false
	 */
	public static boolean checkPermission(Member member, String[] permissions) {
		List<Role> roleList = member.getRoles();
		for (String p : permissions) {
			for (Role r : roleList) {
				if (r.getName().equalsIgnoreCase(p)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void addCommand() {
		CommandHandler.registerCommand("join", new DevJoin());
		CommandHandler.registerCommand("ping", new Ping("Ping", COMMANDS.PING_syntax, COMMANDS.PING_helptxt));
		CommandHandler.registerCommand("asr", new AddServerRole("AddServerRole", COMMANDS.ASR_syntax, COMMANDS.ASR_helptxt));
		CommandHandler.registerCommand("prune", new Prune("Prune", COMMANDS.PRUNE_syntax, COMMANDS.PRUNE_helptxt));
		CommandHandler.registerCommand("rules_en", new Rules_en("Rules_en", COMMANDS.RULES_syntax, COMMANDS.RULES_helptxt));
		CommandHandler.registerCommand("rules_de", new Rules_de("Rules_de", COMMANDS.RULES_syntax, COMMANDS.RULES_helptxt));
		CommandHandler.registerCommand(new String[] {"autochannel", "ac"}, new AutoChannel("AutoChannel", null, null)); // TODO command to show all autochannels
		CommandHandler.registerCommand(new String[] {"ticket", "ticketgta"}, new Ticket("Ticket", COMMANDS.TICKET_syntax, COMMANDS.TICKET_helptxt));
	}
	
	public static void addListeners() {
		builder.addEventListener(new ReadyListener());
		builder.addEventListener(new MemberJoinListener());
		builder.addEventListener(new MessageListener());
		builder.addEventListener(new NicknameChangeListener());
		builder.addEventListener(new RoleChangeListener());
		builder.addEventListener(new ChannelListener());
		builder.addEventListener(new ReactionAddListener());
	}


}
