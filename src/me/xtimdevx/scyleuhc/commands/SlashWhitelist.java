package me.xtimdevx.scyleuhc.commands;

import me.xtimdevx.scyleuhc.utils.Messages;
import net.minecraft.server.v1_8_R3.WhiteList;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.entity.Player;

public class SlashWhitelist implements CommandExecutor{
	
	WhiteList whitelist = ((CraftServer)Bukkit.getServer()).getHandle().getWhitelist();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("whitelist")) {
			if(!p.hasPermission("uhc.command.whitelist")) {
				p.sendMessage(Messages.NOPERMMESSAGE);
				return true;
			}
			
			if(args.length == 0) {
				p.sendMessage(Messages.PREFIX.replace("UHC", "WHITELIST") + "Whitelist commands:");
				p.sendMessage(Messages.ERROR + "/whitelist on");
				p.sendMessage(Messages.ERROR + "/whitelist off");
				p.sendMessage(Messages.ERROR + "/whitelist clear");
				p.sendMessage(Messages.ERROR + "/whitelist add");
				p.sendMessage(Messages.ERROR + "/whitelist removed");
				p.sendMessage(Messages.ERROR + "/whitelist all");
				p.sendMessage(Messages.ERROR + "/whitelist list");
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("on")) {
					Bukkit.setWhitelist(true);
					for(Player online : Bukkit.getOnlinePlayers()) {
						online.sendMessage(Messages.PREFIX.replace("UHC", "WHITELIST") + "Turned on the whitelist.");
					}
				}
				if(args[0].equalsIgnoreCase("off")) {
					Bukkit.setWhitelist(false);
					for(Player online : Bukkit.getOnlinePlayers()) {
						online.sendMessage(Messages.PREFIX.replace("UHC", "WHITELIST") + "Turned off the whitelist.");
					}
				}
				if(args[0].equalsIgnoreCase("clear")) {
					whitelist.getValues().clear();
					for(Player online : Bukkit.getOnlinePlayers()) {
						online.sendMessage(Messages.PREFIX.replace("UHC", "WHITELIST") + "Cleared the whitelist.");
					}
				}
				if(args[0].equalsIgnoreCase("add")) {
					p.sendMessage(Messages.ERROR + "Usage: /whitelist add <player>");
				}
				
				if(args[0].equalsIgnoreCase("remove")) {
					p.sendMessage(Messages.ERROR + "Usage: /whitelist remove <player>");
				}
				
				if(args[0].equalsIgnoreCase("all")) {
					for(Player online : Bukkit.getOnlinePlayers()) {
						online.setWhitelisted(true);
						online.sendMessage(Messages.PREFIX.replace("UHC", "WHITELIST") + "Everyone has been whitelisted.");
					}
				}
				if(args[0].equalsIgnoreCase("list")) {
					if(Bukkit.getWhitelistedPlayers().size() <= 0) {
						p.sendMessage(Messages.PREFIX.replace("UHC", "WHITELIST") + "There are no whitelisted players.");
						return true;
					}
					
					StringBuilder list = new StringBuilder();
					int i = 1;
					
					for(OfflinePlayer whitelisted : Bukkit.getWhitelistedPlayers()) {
						if(list.length() > 0) {
							if(i == Bukkit.getWhitelistedPlayers().size()) {
								list.append(" and ");
							}else{
								list.append(", ");
							}
						}
						
						list.append(whitelisted.getName());
						i++;
					}
					
					p.sendMessage(Messages.PREFIX.replace("UHC", "WHITELIST") + "§8(§c" + (i - 1) + "§8) §7" + list.toString());
				}
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("add")) {
					@SuppressWarnings("deprecation")
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
					target.setWhitelisted(true);
					for(Player online : Bukkit.getOnlinePlayers()) {
						online.sendMessage(Messages.PREFIX.replace("UHC", "WHITELIST") + "Added §c" + target.getName() + " §7to the whitelist.");
					}
				}
				if(args[0].equalsIgnoreCase("remove")) {
					@SuppressWarnings("deprecation")
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
					target.setWhitelisted(false);
					for(Player online : Bukkit.getOnlinePlayers()) {
						online.sendMessage(Messages.PREFIX.replace("UHC", "WHITELIST") + "Removed §c" + target.getName() + " §7from the whitelist.");
					}
				}
			}
		}
		return true;
	}

}
