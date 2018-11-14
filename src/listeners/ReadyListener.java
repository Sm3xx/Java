package listeners;

import containers.ExceptionContainer;
import core.Logger;
import core.handlers.GuildHandler;
import core.handlers.RoleHandler;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter{

	public void onReady(ReadyEvent event) {
		System.out.println("===============================================================");
		Logger.info("Bot online\n");
		System.out.println("===============================================================");
		
		ExceptionContainer error = RoleHandler.loadRoleNames();
		if (error != null) {
			System.out.println("\n");
			Logger.error(error.getMessage());
		}
		
		ExceptionContainer error1 = GuildHandler.loadChannelNames();
		if (error1 != null) {
			System.out.println("\n");
			Logger.error(error1.getMessage());
		}
	}
	
}
