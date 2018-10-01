package commands;

import java.util.List;

import core.Main;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class test implements Command{

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return false;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		Guild guild = Main.getGuild(event.getAuthor());
		List<Emote> emoteList = guild.getEmotes();
		System.out.println(emoteList);
		System.out.println(emoteList.get(0));
		Message msg = event.getChannel().sendMessage("Test").complete();
		
		msg.addReaction(emoteList.get(0)).queue();
		return true;
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

}
