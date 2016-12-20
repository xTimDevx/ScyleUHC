package me.xtimdevx.scyleuhc.commands;

import me.xtimdevx.scyleuhc.State;
import me.xtimdevx.scyleuhc.utils.Messages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SlashSetstate implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("setstate")) {
			if(!p.hasPermission("uhc.command.setstate")) {
				p.sendMessage(Messages.NOPERMMESSAGE);
				return true;
			}
			
			if(args.length == 0) {
				p.sendMessage(Messages.PREFIX.replace("UHC", "STATE") + "Current state: §c" + State.getState());
				p.sendMessage(Messages.PREFIX.replace("UHC", "STATE") + "States: §cingame§7, §cscattering§7, §clobby§7, §cclosed§7 and §cpregame§7.");
			}
			
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("ingame")) {
					State.setState(State.ingame);
					p.sendMessage(Messages.PREFIX.replace("UHC", "STATE") + "You set the state to §cingame§7.");
				}
				if(args[0].equalsIgnoreCase("scattering")) {
					State.setState(State.scattering);
					p.sendMessage(Messages.PREFIX.replace("UHC", "STATE") + "You set the state to §cscattering§7.");
				}
				if(args[0].equalsIgnoreCase("lobby")) {
					State.setState(State.lobby);
					p.sendMessage(Messages.PREFIX.replace("UHC", "STATE") + "You set the state to §clobby§7.");
				}
				if(args[0].equalsIgnoreCase("closed")) {
					State.setState(State.closed);
					p.sendMessage(Messages.PREFIX.replace("UHC", "STATE") + "You set the state to §cclosed§7.");
				}
				if(args[0].equalsIgnoreCase("pregame")) {
					State.setState(State.pregame);
					p.sendMessage(Messages.PREFIX.replace("UHC", "STATE") + "You set the state to §cpregame§7.");
				}
			}
		}
		return true;
	}

}
