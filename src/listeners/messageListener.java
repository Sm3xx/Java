package listeners;

import core.CommandHandler;
import core.CommandParser;
import core.Main;
import core.embedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.STATIC;

public class messageListener extends ListenerAdapter{

	public void onMessageReceived(MessageReceivedEvent event) {
		
		if (event.getMessage().getContentDisplay().startsWith(STATIC.PREFIX) && !event.getMessage().getAuthor().isBot()) {
			CommandHandler.handleCommand(CommandParser.parser(event.getMessage().getContentDisplay(), event));
		}
		
		if (event.getChannel().getName().equalsIgnoreCase(STATIC.GTA_TICKETS_DUMP) && event.getAuthor().isBot()) {
			String msg = event.getMessage().getContentDisplay();
			String author = event.getAuthor().getName();
			msg = msg.replace("/ticketgta", "");
			
			MessageEmbed message = embedBuilder.buildTicket(msg.split(" "), author, STATIC.FIVE);
			Main.getChannel(STATIC.GTA_TICKETS, event.getGuild()).sendMessage(message).queue();
		}
		
	}
	
}
