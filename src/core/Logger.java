package core;

import core.handlers.GuildHandler;
import net.dv8tion.jda.core.entities.TextChannel;
import util.EMOTES;
import util.STATIC;

public class Logger {
	
	public static void info(String message) {
		System.out.println("\n[INFO]     -  ["+Main.getTimestamp()+"] "+message);
	}
	
	public static void warning(String message) {
		System.out.println("\n[WARNING]  -  ["+Main.getTimestamp()+"] "+message);
	}
	
	public static void error(String message) {
		System.out.println("[ERROR]    -  ["+Main.getTimestamp()+"] "+message);
		if (STATIC.ERROR_LOG != "") {
			TextChannel log = GuildHandler.getChannel(STATIC.ERROR_LOG);
			if (log != null) {
				String title = EMOTES.BANGBANG + " "+ Main.getTimestamp();
				log.sendMessage(MessageBuilder.buildEmbed(title, message, null, false)).queue();
			}
		}
	}
	
	public static void message(String message) {
		System.out.println("\n[MESSAGE]  -  ["+Main.getTimestamp()+"] "+message);
	}
	
	public static void command(String message) {
		System.out.println("\n[COMMAND]  -  ["+Main.getTimestamp()+"] "+message);
	}
}
