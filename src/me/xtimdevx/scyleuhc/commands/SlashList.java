package me.xtimdevx.scyleuhc.commands;

import me.xtimdevx.scyleuhc.utils.Messages;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SlashList implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("list")) {
			StringBuilder list = new StringBuilder();
			int i = 1;
			for(Player online : Bukkit.getOnlinePlayers()) {
				if(list.length() > 0) {
					if(i == Bukkit.getOnlinePlayers().size()) {
						list.append("§7 and ");
					}else{
						list.append("§7, ");
					}
				}
				
				list.append(online.getName());
				i++;
			}
			
			p.sendMessage(Messages.PREFIX.replace("UHC", "LIST") + "§8(§c" + (i - 1) + "§8) §7" + list.toString());
		}
		return true;
	}
	
}
