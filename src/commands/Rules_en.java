package commands;

import java.awt.Color;

import core.Main;
import core.MessageBuilder;
import core.Logger;
import core.ErrorHandler;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.RULES;

public class Rules_en implements ICommand{

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		try {
			Member member = Main.getGuildMember(event.getAuthor());
			if (member != null) {
				return false;
			}
		} catch (Exception e) {
			return true;
		}
		return true;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		
		try {
			MessageEmbed msg = MessageBuilder.buildEmbed(RULES.EN_TITLE, RULES.EN, Color.WHITE, true);
			if (event.getPrivateChannel() != null) {
				event.getChannel().sendMessage(msg).queue();
			} else {
				Member member = Main.getGuildMember(event.getAuthor());
				MessageBuilder.sendPrivateMessage(member.getUser(), msg, "Rules_en");
			}
			return true;
		} catch (Exception e ) {
			return false;
		}
		
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		Member member = Main.getGuildMember(event.getAuthor());
		Logger.command("Rules_en called by "+Main.getUserName(member)+" [Executed: "+success+"]");
		Logger.message("Rules sent to "+Main.getUserName(member));
		
	}
	
	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		Logger.error("Rules_en called by "+name+" [Executed: "+success+"]");

		ErrorHandler.cmdErr(event, "Error in runtime - Command: RULES_EN");
		
		Logger.message("Errormessage send to "+name);
	}

	@Override
	public String help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

}
