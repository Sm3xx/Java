package core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import commands.AddServerRole;
import commands.AutoChannel;
import commands.DevJoin;
import commands.Ping;
import commands.Prune;
import commands.Rules;
import commands.Test;
import commands.Ticket;
import commands.core.CommandHandler;
import listeners.ChannelChangeListener;
import listeners.MemberJoinListener;
import listeners.MessageListener;
import listeners.NicknameChangeListener;
import listeners.ReactionAddListener;
import listeners.ReadyListener;
import listeners.RoleChangeListener;
import listeners.VoiceChannelListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import util.COMMANDS;
import util.SECRETS;
import util.STATIC;

public class Main {
	
	static JDABuilder builder = new JDABuilder (AccountType.BOT);
	static JDA jda;
	
	public static void main(String[] Args) {
		
		
		builder.setToken(SECRETS.TOKEN);
		builder.setAutoReconnect(true);
		builder.setGame(Game.playing("v"+STATIC.VERSION));
		
		addListeners();
		addCommand();
		getTimestamp();
		
		try {
			jda = builder.build();
		} catch (Exception e) {}
		
	}
	
	public static JDA getJDA() {
		return jda;
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
	
	public static void addCommand() {
		CommandHandler.registerCommand("join", new DevJoin(null, null, null));
		CommandHandler.registerCommand("test", new Test());
		CommandHandler.registerCommand("ping", new Ping("Ping", COMMANDS.PING_syntax, COMMANDS.PING_helptxt));
		CommandHandler.registerCommand("asr", new AddServerRole("AddServerRole", COMMANDS.ASR_syntax, COMMANDS.ASR_helptxt));
		CommandHandler.registerCommand("prune", new Prune("Prune", COMMANDS.PRUNE_syntax, COMMANDS.PRUNE_helptxt));
		CommandHandler.registerCommand("rules", new Rules("Rules", COMMANDS.RULES_syntax, COMMANDS.RULES_helptxt));
		CommandHandler.registerCommand(new String[] {"autochannel", "ac"}, new AutoChannel("AutoChannel", COMMANDS.AC_syntax, COMMANDS.AC_helptxt));
		CommandHandler.registerCommand(new String[] {"ticket", "ticketgta"}, new Ticket("Ticket", COMMANDS.TICKET_syntax, COMMANDS.TICKET_helptxt));
	}
	
	public static void addListeners() {
		builder.addEventListener(new ReadyListener());
		builder.addEventListener(new MemberJoinListener());
		builder.addEventListener(new MessageListener());
		builder.addEventListener(new NicknameChangeListener());
		builder.addEventListener(new RoleChangeListener());
		builder.addEventListener(new ChannelChangeListener());
		builder.addEventListener(new ReactionAddListener());
		builder.addEventListener(new VoiceChannelListener());
	}


}
