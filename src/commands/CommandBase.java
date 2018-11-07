package commands;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;

public class CommandBase {
	protected EmbedBuilder error = new EmbedBuilder().setColor(Color.red); 
	
	protected String cmdName;
	protected String syntax;
	protected String helpTxt;
	
	public CommandBase (String cmdName, String syntax, String helptxt) {
		this.cmdName = cmdName;
		this.syntax = syntax;
		this.helpTxt = helptxt;
	}
}
