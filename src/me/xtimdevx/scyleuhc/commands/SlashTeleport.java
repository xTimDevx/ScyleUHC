package me.xtimdevx.scyleuhc.commands;

import java.util.ArrayList;
import java.util.Random;

import me.xtimdevx.scyleuhc.utils.Messages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class SlashTeleport implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("teleport")) {
			if(!p.hasPermission("uhc.command.teleport")) {
				p.sendMessage(Messages.NOPERMMESSAGE);
				return true;
			}
			
			if(args.length == 0) {
				p.sendMessage(Messages.PREFIX.replace("UHC", "TELEPORT") + "Teleport usage:");
				p.sendMessage(Messages.ERROR + "/teleport <player>");
				p.sendMessage(Messages.ERROR + "/teleport <player> <target>");
				p.sendMessage(Messages.ERROR + "/teleport <x> <y> <z>");
				p.sendMessage(Messages.ERROR + "/teleport map");
				p.sendMessage(Messages.ERROR + "/teleport random");
				return true;
			}
			
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("map")) {
					p.sendMessage(Messages.PREFIX.replace("UHC", "TELEPORT") + "You teleported to the uhc §cmap§7.");
					p.teleport(new Location(Bukkit.getWorld("UHC"), 0, 80, 0));
				}
				if(args[0].equalsIgnoreCase("random")) {
					Random r = new Random();
			          ArrayList<Player> players = new ArrayList<Player>();
			          for(Player online : Bukkit.getServer().getOnlinePlayers()) {
			          if(online == p) {
			          }
			          else
			          players.add(online);
			          }
			          int index = r.nextInt(players.size());
			          Player loc = (Player) players.get(index);
			          p.teleport(loc);
							p.sendMessage(Messages.PREFIX.replace("UHC", "TELEPORT") + "Teleporting to §c" + loc.getPlayer().getName() + "§7.");
						    if (loc.getPlayer().getScoreboard().getEntryTeam(loc.getPlayer().getName()) != null) { 
						    	StringBuilder list = new StringBuilder(""); 
							    int i = 1; 
							    Team team = loc.getPlayer().getScoreboard().getEntryTeam(loc.getPlayer().getName()); 
							    for (String entry : SlashTeam.savedTeams.get(team.getName())) { 
							     if (list.length() > 0) { 
							      if (i == SlashTeam.savedTeams.get(team.getName()).size()) { 
							       list.append(" §fand §f"); 
							      } else { 
							       list.append("§7, §7"); 
							      } 
							     } 
							      
								@SuppressWarnings("deprecation")
								OfflinePlayer teammates = Bukkit.getOfflinePlayer(entry); 
							      
							     if (teammates.isOnline()) { 
							      list.append(ChatColor.GREEN + teammates.getName()); 
							     } else { 
							      list.append(ChatColor.RED + teammates.getName()); 
							     } 
							     i++; 
							    } 
							    
							    p.sendMessage(Messages.PREFIX.replace("UHC", "TELEPORT") + "Team: " + list.toString().trim());
							}else{
							    p.sendMessage(Messages.PREFIX.replace("UHC", "TELEPORT") + "Team: No team members");								
							}
						}
						
					}
		}
		return true;
	}

}
