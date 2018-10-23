package listeners;

import core.Logger;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class readyListener extends ListenerAdapter{

	public void onReady(ReadyEvent event) {
		System.out.println("===============================================================");
		Logger.info("Bot logged in\n");
		System.out.println("===============================================================");
	}
	
}
