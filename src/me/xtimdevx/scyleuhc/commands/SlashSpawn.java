package me.xtimdevx.scyleuhc.commands;

import me.xtimdevx.scyleuhc.State;
import me.xtimdevx.scyleuhc.utils.Messages;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SlashSpawn implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("spawn")) {
			if(!p.hasPermission("uhc.command.spawn")) {
				p.sendMessage(Messages.NOPERMMESSAGE);
				return true;
			}
			
			if(!State.isState(State.closed)) {
				p.sendMessage(Messages.ERROR + "You cant use this command after the game has started.");
				return true;
			}
			if(args.length == 0) {
				p.sendMessage(Messages.PREFIX.replace("UHC", "SPAWN") + "Teleporting §cyou §7to §cspawn§7.");
				double x0 = -545.5;
		        double y0 = 15;
		        double z0 = -554.5;
		        org.bukkit.Location loc0 = new org.bukkit.Location(Bukkit.getWorld("HUB"), x0, y0, z0);
		        loc0.setYaw(90F);
		        loc0.setPitch(0F);
		        p.teleport(loc0);
			}
			
			if(args.length == 1) {
				Player target = Bukkit.getPlayer(args[1]);
				if(target == null) {
					p.sendMessage(Messages.ERROR + "This player is not online.");
					return true;
				}
				
				p.sendMessage(Messages.PREFIX.replace("UHC", "SPAWN") + "Teleporting §c" + target.getName() + " §7to §cspawn§7.");
				target.sendMessage(Messages.PREFIX.replace("UHC", "SPAWN") + "Teleporting §cyou §7to §cspawn§7.");
				double x0 = -545.5;
		        double y0 = 15;
		        double z0 = -554.5;
		        org.bukkit.Location loc0 = new org.bukkit.Location(Bukkit.getWorld("HUB"), x0, y0, z0);
		        loc0.setYaw(90F);
		        loc0.setPitch(0F);
		        target.teleport(loc0);
		        }
			
		}
		return true;
	}

}
