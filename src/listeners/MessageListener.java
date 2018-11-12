package listeners;

import commands.core.CommandHandler;
import commands.core.CommandParser;
import core.MessageBuilder;
import core.handlers.GuildHandler;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.EMOTES;
import util.STATIC;

public class MessageListener extends ListenerAdapter{

	public void onMessageReceived(MessageReceivedEvent event) {
		
		if (event.getMessage().getContentDisplay().startsWith(STATIC.PREFIX) && !event.getMessage().getAuthor().isBot()) {
			CommandHandler.handleCommand(CommandParser.parser(event.getMessage().getContentDisplay().toLowerCase(), event));
		}
		
		if (STATIC.ACTIVE_TICKET_BOT) {
			if (event.getChannel().getName().equalsIgnoreCase(STATIC.GTA_TICKETS_DUMP) && event.getAuthor().isBot()) {
				String msg = event.getMessage().getContentDisplay();
				String author = event.getAuthor().getName();
				msg = msg.replace("/ticketgta", "");
				
				MessageEmbed message = MessageBuilder.buildTicket(msg.split(" "), author, EMOTES.FIVE);
				GuildHandler.getChannel(STATIC.GTA_TICKETS).sendMessage(message).queue();
			}
		}
		
	}
	
}
