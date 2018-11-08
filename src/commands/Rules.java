package commands;

import commands.core.CommandBase;
import commands.core.ICommand;
import core.Logger;
import core.Main;
import core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.RULES;
import util.STATIC;

public class Rules extends CommandBase implements ICommand{

	public Rules(String cmdName, String syntax, String helptxt) {
		super(cmdName, syntax, helptxt);
	}

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		if (calledHelp(args, event)) {
			return true;
		}
		
		if (args.length == 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		MessageEmbed msg;
		switch (args[0].toLowerCase()) {
		case "de":
			msg = MessageBuilder.buildEmbed(RULES.DE_TITLE, RULES.DE, STATIC.EMBED_COLOR, true);
			event.getChannel().sendMessage(msg).queue();
			break;
		default:
			msg = MessageBuilder.buildEmbed(RULES.EN_TITLE, RULES.EN, STATIC.EMBED_COLOR, true);
			event.getChannel().sendMessage(msg).queue();
			break;
		}
		return false;
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		Member member = Main.getGuildMember(event.getAuthor());
		Logger.command(this.cmdName + " called by "+Main.getUserName(member)+" [Executed: "+success+"]");
		Logger.message("Rules sent to "+Main.getUserName(member));		
	}

	@Override
	public void error(boolean success, MessageReceivedEvent event) {}

}
