package listeners;

import core.CommandHandler;
import core.CommandParser;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.STATIC;

public class commandListener extends ListenerAdapter{

	public void onMessageReceived(MessageReceivedEvent event) {
		
		if (event.getMessage().getContentDisplay().startsWith(STATIC.PREFIX) && !event.getMessage().getAuthor().isBot()) {
			CommandHandler.handleCommand(CommandParser.parser(event.getMessage().getContentDisplay(), event));
		}
		
	}
	
}
