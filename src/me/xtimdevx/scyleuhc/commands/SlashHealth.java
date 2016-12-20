package me.xtimdevx.scyleuhc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.xtimdevx.scyleuhc.utils.Messages;

public class SlashHealth implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender; 
		if(cmd.getName().equalsIgnoreCase("health")) {
			if(args.length == 0) {
				long health = Math.round(p.getHealth());
				p.sendMessage(Messages.PREFIX.replace("UHC", "HEALTH") + "You: §C" + health);
			}
			if(args.length == 1) {
				Player target = Bukkit.getPlayer(args[1]);
				if(target == null) {
					p.sendMessage(Messages.ERROR + "This player is not online.");
					return true;
				}
				long health = Math.round(target.getHealth());
				p.sendMessage(Messages.PREFIX.replace("UHC", "HEALTH") + target.getName() + ": §C" + health);
			}
		}
		return true;
	}

}
