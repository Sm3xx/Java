package commands;

import java.awt.Color;

import commands.core.CommandBase;
import commands.core.ICommand;
import core.Logger;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.MESSAGES;

public class Ticket extends CommandBase implements ICommand {

	public Ticket(String cmdName, String syntax, String helptxt) {
		super(cmdName, syntax, helptxt);
	}

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		return false;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		try {
			MessageEmbed msg = new EmbedBuilder().setDescription(MESSAGES.TICKET_MESSAGE_DE + "\n\n" + MESSAGES.TICKET_MESSAGE_EN + "\n\n" + MESSAGES.TICKET_LINK)
					.setColor(Color.green).build();
			event.getChannel().sendMessage(msg).queue();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		String name = getUsername(event.getAuthor());
		Logger.command(this.cmdName + " called by " + name + " [Executed: " + success + "]");
		Logger.message("Ticket link sent to " + name);
	}

	@Override
	public void error(boolean success, MessageReceivedEvent event) {
		Logger.error(this.cmdName + " called by " + getUsername(event.getAuthor()) + " [Executed: " + success + "]");
	}

}
