package listeners;

import core.Logger;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter{

	public void onReady(ReadyEvent event) {
		System.out.println("===============================================================");
		Logger.info("Bot online\n");
		System.out.println("===============================================================");
	}
	
}
