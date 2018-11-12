package commands;

import java.awt.Color;
import java.util.HashMap;

import commands.core.CommandBase;
import commands.core.ICommand;
import core.Logger;
import core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.EMOTES;
import util.PERMISSIONS;

public class AutoChannel extends CommandBase implements ICommand{
	
	private static HashMap<String, Guild> ac = new HashMap<>();
	private String operation;

	public AutoChannel(String cmdName, String syntax, String helptxt) {
		super(cmdName, syntax, helptxt);
	}
	
	public static HashMap<String, Guild> getAutoChan() {
		return ac;
	}

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		operation = "";
		TextChannel channel;
		
		if (calledHelp(args, event)) {
			return true;
		}
		
		if (event.getChannelType().toString() == "PRIVATE") {
			MessageBuilder.sendInformationMessage(event.getChannel(), Color.red, "Command not executable in Private Channels");
			return true;
		}
		
		channel = event.getTextChannel();
		
		if (checkPermission(event.getMember(), PERMISSIONS.ADMIN_COMMAND)) {
			if (args.length == 2) {
				String cid = args[1];
				try {
					event.getGuild().getVoiceChannelById(cid);
					switch (args[0].toLowerCase()) {
						case "add":
							if (!ac.containsKey(cid) ) {
								return false;
							} else {
								MessageBuilder.sendInformationMessage(channel, Color.red, "Already entered as autochannel");
								return true;
							}
						case "remove":
							if (ac.containsKey(cid)) {
								return false;
							} else {
								MessageBuilder.sendInformationMessage(channel, Color.red, "Channel not defined as autochannel");
								return true;
							}							
					}
				} catch (NumberFormatException e) {
					MessageBuilder.sendInformationMessage(channel, Color.red, "Enter a valid channel id");
					return true;
				}
				
			} else if (args.length == 1 && args[0].equalsIgnoreCase("show")) {
				if (ac.keySet().toArray().length == 0) {
					event.getChannel().sendMessage(error.setDescription("No autochannels entered yet!").build()).queue();
					return true;
				}
				
				String channels = "";
				for (Object key : ac.keySet().toArray()) {
					String channelname = event.getGuild().getVoiceChannelById(key.toString()).getName();
					channels = channels + EMOTES.WHITE_SQUARE + " " + channelname + "\n";
				}
				
				event.getChannel().sendMessage(error.setTitle("Autochannels:").setDescription(channels).build()).queue();
				return true;
				
			} else {
				MessageBuilder.sendInformationMessage(channel, Color.red, "Enter a channel id");
				return true;
			}
		}
		return true;
	}

	@Override
	public boolean action(String[] args, MessageReceivedEvent event) {
		operation = args[0].toLowerCase();
		String id = args[1];
		
		switch (operation) {
			case "add":
				ac.put(id, event.getGuild());
				return true;				
			case "remove":
				ac.remove(id);
				return true;
			default:
				return false;
		}
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		switch(operation) {
			case "add":
				MessageBuilder.sendInformationMessage(event.getChannel(), Color.green, "Autochannel added!");
				break;
			case "remove":
				MessageBuilder.sendInformationMessage(event.getChannel(), Color.green, "Autochannel removed!");
				break;
			default:
				break;
		}
		
		Logger.command(this.cmdName + " called by "+getUsername(event.getAuthor())+" [Executed: "+success+"]");
	}

}
