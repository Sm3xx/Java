package commands;

import java.awt.Color;

import core.Main;
import core.embedBuilder;
import core.ConsoleLogger;
import core.ErrorHandler;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.RULES;

public class rulesDE implements Command{

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		try {
			Member member = Main.getGuildMember(event.getAuthor());
			if (member != null) {
				return false;
			}
		} catch (Exception e) {
			ConsoleLogger.error(e.getMessage());
			return true;
		}
		return true;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		try {
			MessageEmbed msg = embedBuilder.buildEmbed(RULES.DE_TITLE, RULES.DE, Color.WHITE, true);
			if (event.getPrivateChannel() != null) {
				event.getChannel().sendMessage(msg).queue();
			} else {
				Member member = Main.getGuildMember(event.getAuthor());
				Main.sendPrivateMessage(member.getUser(), msg, "Rules_de");
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		Member member = Main.getGuildMember(event.getAuthor());
		ConsoleLogger.command("Rules_de called by "+Main.getUserName(member)+" [Executed: "+success+"]");
		ConsoleLogger.message("Rules sent to "+Main.getUserName(member));
		
	}
	
	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		String name = Main.getUserName(Main.getGuildMember(event.getAuthor()));
		ConsoleLogger.error("Rules_de called by "+name+" [Executed: "+success+"]");

		ErrorHandler.cmdErr(event, "Error in runtime - Command: RULES_DE");
		
		ConsoleLogger.message("Errormessage send to "+name);
	}

	@Override
	public String help(MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

}
