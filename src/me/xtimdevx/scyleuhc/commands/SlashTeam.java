package me.xtimdevx.scyleuhc.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.xtimdevx.scyleuhc.Main;
import me.xtimdevx.scyleuhc.Settings;
import me.xtimdevx.scyleuhc.utils.Messages;
import me.xtimdevx.scyleuhc.utils.ScoreBoard;
import me.xtimdevx.scyleuhc.utils.Teams;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class SlashTeam implements CommandExecutor, TabCompleter{

	public static HashMap<Player, List<Player>> invites = new HashMap<Player, List<Player>>(); 
	 public static HashMap<String, List<String>> savedTeams = new HashMap<String, List<String>>(); 
	 
	 @SuppressWarnings({ "static-access", "deprecation" })
	@Override 
	 public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) { 
	  Teams teams = Teams.getInstance(); 
	  Player pl = (Player) sender;
	   
	  if (args.length == 0) { 
	   sendHelp(sender); 
	   return true; 
	  } 
	   
	  if (args.length > 1) { 
	   Player target = Bukkit.getServer().getPlayer(args[1]); 
	    
	   if (args[0].equalsIgnoreCase("info")) { 
	    if (!sender.hasPermission("uhc.team")) { 
	     sendHelp(sender); 
	     return true; 
	    } 
	     
	    if (target == null) { 
	     sender.sendMessage(Messages.ERROR + args[1] + " is not online."); 
	     return true; 
	    } 
	     
	    Team team = target.getScoreboard().getEntryTeam(target.getName()); 
	    
	    if (!savedTeams.containsKey(team.getName())) { 
	     ArrayList<String> players = new ArrayList<String>(team.getEntries()); 
	     SlashTeam.savedTeams.put(team.getName(), players); 
	    } 
	     
	    StringBuilder list = new StringBuilder(""); 
	    int i = 1; 
	     
	    for (String entry : savedTeams.get(team.getName())) { 
	     if (list.length() > 0) { 
	      if (i == savedTeams.get(team.getName()).size()) { 
	       list.append(" §fand §f"); 
	      } else { 
	       list.append("§7, §7"); 
	      } 
	     } 
	      
		OfflinePlayer teammates = Bukkit.getOfflinePlayer(entry); 
	      
	     if (teammates.isOnline()) { 
	      list.append(ChatColor.GREEN + teammates.getName()); 
	     } else { 
	      list.append(ChatColor.RED + teammates.getName()); 
	     } 
	     i++; 
	    } 
	     
	    sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + ChatColor.GREEN + target.getName() + "'s §7team info:"); 
	    sender.sendMessage("§8[§cTEAM§8]: §7Team: " + team.getPrefix() + team.getName()); 
	    sender.sendMessage("§8[§cTEAM§8]: §7Teammates - (Names in red means they are offline)"); 
	    sender.sendMessage("§8» " + list.toString().trim());
	    pl.sendTitle("§c§lTEAM", "§8[§cUHC§8]: §7" + list.toString().trim());
	    
	    return true; 
	   } 
	   
	   if (args[0].equalsIgnoreCase("invite")) { 
	    if (!(sender instanceof Player)) { 
	     sender.sendMessage(Messages.ERROR + "Only players can create and manage teams."); 
	     return true; 
	    } 
	     
	    Player player = (Player) sender; 
	     
	    if (!Settings.getInstance().getData().get("teamsize.teammode").equals("team")) { 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "Team management is currently disabled."); 
	     return true; 
	    } 
	     
	    Team team = player.getScoreboard().getEntryTeam(sender.getName()); 
	     
	    if (team == null) { 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "You are not on a team."); 
	     return true; 
	    } 
	     
	    if (target == null) { 
	     sender.sendMessage(Messages.ERROR + args[1] + " is not online."); 
	     return true; 
	    } 
	     
	    if (team.getSize() >= Main.teamlimit) { 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "Your team is currently full."); 
	     return true; 
	    } 
	     
	    Team team1 = player.getScoreboard().getEntryTeam(target.getName()); 
	     
	    if (team1 != null) { 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "That player is already on a team."); 
	     return true; 
	    } 
	     
	    teams.sendMessage(team, Messages.PREFIX.replace("UHC", "TEAM") + ChatColor.RED + target.getName() + " §7has been invited to your team.");
	    teams.sendTitle(team, "§c" + target.getName() + " §7has been invited to your team.");
	 
	    if (!invites.containsKey(sender)) { 
	     invites.put(player, new ArrayList<Player>()); 
	    } 
	    invites.get(sender).add(target); 
	    target.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "You have been invited to §c" + sender.getName() + "'s §7team.");
	    target.sendTitle("§c§lTEAM", "§7You have been invited to §c" + sender.getName() + "'s §7team.");
	     
	    ComponentBuilder builder = new ComponentBuilder(""); 
	    builder.append(Messages.PREFIX.replace("UHC", "TEAM") + "§7§oClick here to accept his request."); 
	    builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team accept " + sender.getName())); 
	    builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[] { new TextComponent("§7§oClick here to join the team") })); 
	    target.spigot().sendMessage(builder.create()); 
	    return true; 
	   } 
	    
	   if (args[0].equalsIgnoreCase("kick")) { 
	    if (!(sender instanceof Player)) { 
	     sender.sendMessage(ChatColor.RED + "Only players can create and manage teams."); 
	     return true; 
	    } 
	     
	    Player player = (Player) sender; 
	     
	    if (!Settings.getInstance().getData().get("teamsize.teammode").equals("team")) { 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "Team management is currently disabled."); 
	     return true; 
	    } 
	     
	    Team team = player.getScoreboard().getEntryTeam(sender.getName()); 
	     
	    if (team == null) { 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "You are not on a team."); 
	     return true; 
	    } 
	     
	    if (target == null) { 
	     sender.sendMessage(Messages.ERROR + args[1] + " is not online."); 
	     return true; 
	    } 
	     
	    if (!team.getEntries().contains(target.getName())) { 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "That player is not on your team."); 
	     return true; 
	    } 
	     
	    team.removeEntry(target.getName()); 
	    target.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "You got kicked out of your team."); 
	     
	    ArrayList<String> players = new ArrayList<String>(team.getEntries()); 
	    SlashTeam.savedTeams.put(team.getName(), players); 
	    teams.sendMessage(team, Messages.PREFIX.replace("UHC", "TEAM") + ChatColor.RED + target.getName() + " §7was kicked from your team."); 
	    teams.sendTitle(team, "§8» §c" + target.getName() + " §7has kicked from your team.");
	    return true; 
	   } 
	    
	   if (args[0].equalsIgnoreCase("accept")) { 
	    if (!(sender instanceof Player)) { 
	     sender.sendMessage(ChatColor.RED + "Only players can create and manage teams."); 
	     return true; 
	    } 
	     
	    Player player = (Player) sender; 
	     
	    if (!Settings.getInstance().getData().get("teamsize.teammode").equals("team")) { 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "Team management is currently disabled."); 
	     return true; 
	    } 
	     
	    if (target == null) { 
	     sender.sendMessage(Messages.ERROR + args[1] + " is not online."); 
	     return true; 
	    } 
	     
	    if (player.getScoreboard().getEntryTeam(player.getName()) != null) { 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "You are already on a team."); 
	     return true; 
	    } 
	     
	    if (invites.containsKey(target) && invites.get(target).contains(sender)) { 
	     Team team = target.getScoreboard().getEntryTeam(target.getName()); 
	      
	     if (team == null) { 
	      sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "That player is not on a team."); 
	      return true; 
	     } 
	      
	     if (team.getSize() >= Main.teamlimit) { 
	      sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "That team is currently full."); 
	      return true; 
	     } 
	     
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "Request accepted."); 
	     team.addEntry(sender.getName()); 
	      
	     teams.sendMessage(team, Messages.PREFIX.replace("UHC", "TEAM") + ChatColor.RED + sender.getName() + " §7joined your team."); 
		 teams.sendTitle(team, "§c" + target.getName() + " §7joined your team.");
	      
	     ArrayList<String> players = new ArrayList<String>(team.getEntries()); 
	     SlashTeam.savedTeams.put(team.getName(), players); 
	      
	     invites.get(target).remove(sender); 
	    } else { 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + ChatColor.RED + target.getName() + " §7hasn't sent you any requests."); 
	    } 
	    return true; 
	   } 
	    
	   if (args[0].equalsIgnoreCase("deny")) { 
		    if (!Settings.getInstance().getData().get("teamsize.teammode").equals("team")) { 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "Team management is currently disabled."); 
	     return true; 
	    } 
	     
	    if (target == null) { 
	     sender.sendMessage(ChatColor.RED + args[1] + " is not online."); 
	     return true; 
	    } 
	     
	    if (invites.containsKey(target) && invites.get(target).contains(sender)) { 
	     target.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + ChatColor.RED + sender.getName() + " §7denied your request."); 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "Request denied."); 
	     target.sendTitle("§c§lTEAM", "§c" + sender.getName() + " §7denied your request.");
	      
	     invites.get(target).remove(sender); 
	    } else { 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + ChatColor.RED + target.getName() + " §7hasn't sent you any requests."); 
	    } 
	    return true; 
	   } 
	    
	   if (args[0].equalsIgnoreCase("remove")) { 
	    if (!sender.hasPermission("uhc.team")) { 
	     sendHelp(sender); 
	     return true; 
	    } 
	     
	    if (target == null) { 
	     OfflinePlayer offline = Bukkit.getOfflinePlayer(args[1]); 
	      
	     Team team = teams.getTeam(offline); 
	      
	     if (team == null) { 
	      sender.sendMessage(ChatColor.RED + args[1] + " is not online."); 
	      return true; 
	     } 
	      
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + ChatColor.RED + offline.getName() + " §7was removed from his team."); 
	     teams.leaveTeam(offline); 
	 
	     ArrayList<String> players = new ArrayList<String>(team.getEntries()); 
	     SlashTeam.savedTeams.put(team.getName(), players); 
	     return true; 
	    } 
	     
	    Team team = teams.getTeam(target); 
	     
	    if (team == null) { 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "That player is not on a team."); 
	     return true; 
	    } 
	     
	    sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + ChatColor.RED + target.getName() + " §7was removed from his team."); 
	    teams.leaveTeam(target); 
	 
	    ArrayList<String> players = new ArrayList<String>(team.getEntries()); 
	    savedTeams.put(team.getName(), players); 
	    return true; 
	   } 
	    
	   if (args[0].equalsIgnoreCase("delete")) { 
	    if (!sender.hasPermission("uhc.team")) { 
	     sendHelp(sender); 
	     return true; 
	    } 
	     
	    Team team = teams.getTeam(args[1]); 
	     
	    if (team == null) { 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "That team does not exist."); 
	     return true; 
	    } 
	     
	    for (String p : team.getEntries()) { 
	     team.removeEntry(p); 
	    } 
	     
	    ArrayList<String> players = new ArrayList<String>(team.getEntries()); 
	    SlashTeam.savedTeams.put(team.getName(), players); 
	     
	    sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "Team " + team.getName() + " has been deleted."); 
	    return true; 
	   } 
	    
	   if (args[0].equalsIgnoreCase("friendlyfire")) { 
	    if (!sender.hasPermission("uhc.team")) { 
	     sendHelp(sender); 
	     return true; 
	    } 
	     
	    boolean enable; 
	     
	    if (args[1].equalsIgnoreCase("true")) { 
	     enable = true; 
	    } 
	    else if (args[1].equalsIgnoreCase("false")) { 
	     enable = false; 
	    } 
	    else { 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "Friendlyfire can only be §ctrue §7or §cfalse§7."); 
	     return true; 
	    } 
	     
	    for (Team team : ScoreBoard.getInstance().board.getTeams()) { 
	     team.setAllowFriendlyFire(enable); 
	    } 
	     
	    Bukkit.getServer().broadcastMessage(Messages.PREFIX.replace("UHC", "TEAM") + "FriendlyFire is now " + (enable ? "enabled." : "disabled.")); 
	    for(Player online : Bukkit.getOnlinePlayers()) {
	    	online.sendTitle("§c§lTEAM", "§fFriendlyFire is now §c" + (enable ? "enabled§7." : "disabled§7."));
	    }
	    return true; 
	   } 
	  } 
	  
	  if (args.length > 2) { 
	   if (args[0].equalsIgnoreCase("add")) { 
	    if (!sender.hasPermission("uhc.team")) { 
	     sendHelp(sender); 
	     return true; 
	    } 
	     
	    Team team = teams.getTeam(args[1]); 
	     
	    if (team == null) { 
	     sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "That team does not exist."); 
	     return true; 
	    } 
	     
	    OfflinePlayer offline = Bukkit.getOfflinePlayer(args[2]); 
	     
	    teams.joinTeam(team, offline); 
	     
	    ArrayList<String> players = new ArrayList<String>(team.getEntries()); 
	    SlashTeam.savedTeams.put(team.getName(), players); 
	     
	    sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + ChatColor.RED + offline.getName() + "§7 was added to team " + team.getName() + "."); 
	    return true; 
	   }  
	  } 
	   
	  if (args[0].equalsIgnoreCase("create")) { 
	   if (!(sender instanceof Player)) { 
	    sender.sendMessage(ChatColor.RED + "Only players can create and manage teams."); 
	    return true; 
	   } 
	    
	   Player player = (Player) sender; 
	    
	   if (!Settings.getData().get("teamsize.teammode").equals("team")) { 
	    sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "Team management is currently disabled."); 
	    return true; 
	   } 
	   
	   if (teams.getTeam(player) != null) { 
	    sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "You are already on a team."); 
	    return true; 
	   } 
	    
	   Team team = teams.findAvailableTeam(); 
	    
	   if (team == null) { 
	    sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "There are no more available teams."); 
	    return true; 
	   } 
	    
	   teams.joinTeam(team, player); 
	    
	   ArrayList<String> players = new ArrayList<String>(team.getEntries()); 
	   SlashTeam.savedTeams.put(team.getName(), players); 
	    
	   sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "Team created! Use §c/team invite <player>§7 to invite a player."); 
	   pl.sendTitle("§c§lTEAM", "§7Team created! Use §c/team invite <player>§7 to invite a player.");
	   return true; 
	  } 
	   
	  if (args[0].equalsIgnoreCase("leave")) { 
	   if (!(sender instanceof Player)) { 
	    sender.sendMessage(ChatColor.RED + "Only players can create and manage teams."); 
	    return true; 
	   } 
	    
	   Player player = (Player) sender; 
	    
	   if (!Settings.getData().get("teamsize.teammode").equals("team")) { 
	    sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "Team management is currently disabled."); 
	    return true; 
	   } 
	    
	   Team team = teams.getTeam(player); 
	    
	   if (team == null) { 
	    sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "You are not on a team."); 
	    return true; 
	   } 
	 
	   sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "You left your team."); 
	   teams.leaveTeam(player); 
	    
	   ArrayList<String> players = new ArrayList<String>(team.getEntries()); 
	   SlashTeam.savedTeams.put(team.getName(), players); 
	    
	   teams.sendMessage(team, Messages.PREFIX.replace("UHC", "TEAM") + sender.getName() + " left your team."); 
	   return true; 
	  } 
	   
	  if (args[0].equalsIgnoreCase("info")) { 
	   if (!(sender instanceof Player)) { 
	    sender.sendMessage(ChatColor.RED + "Only players can create and manage teams."); 
	    return true; 
	   } 
	    
	   Player player = (Player) sender; 
	    
	   Team team = teams.getTeam(player); 
	    
	   if (team == null) { 
	    sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "You are not on a team."); 
	    return true; 
	   } 
	    
	   if (!savedTeams.containsKey(team.getName())) { 
	    ArrayList<String> players = new ArrayList<String>(team.getEntries()); 
	    SlashTeam.savedTeams.put(team.getName(), players); 
	   } 
	    
	   StringBuilder list = new StringBuilder(""); 
	   int i = 1; 
	    
	   for (String entry : savedTeams.get(team.getName())) { 
	    if (list.length() > 0) { 
	     if (i == savedTeams.get(team.getName()).size()) { 
	      list.append(" §7fand §7"); 
	     } else { 
	      list.append("§7, §7"); 
	     } 
	    } 
	     
	    OfflinePlayer teammates = Bukkit.getOfflinePlayer(entry); 
	     
	    if (teammates.isOnline()) { 
	     list.append(ChatColor.GREEN + teammates.getName()); 
	    } else { 
	     list.append(ChatColor.RED + teammates.getName()); 
	    } 
	    i++; 
	   } 
	    
	   sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "Your teammates: §o(Names in red means they are offline)"); 
	   sender.sendMessage("§8[§cTEAM§8]: §7" + list.toString().trim()); 
	   return true; 
	  } 
	   
	  if (args[0].equalsIgnoreCase("clear")) { 
	   if (!sender.hasPermission("uhc.team")) { 
	    sendHelp(sender); 
	    return true; 
	   } 
	    
	   if (sender.hasPermission("uhc.team")) { 
	    for (Team team : ScoreBoard.getInstance().board.getTeams()) { 
	     for (String p : team.getEntries()) { 
	      team.removeEntry(p); 
	     } 
	    } 
	     
	    for (String key : savedTeams.keySet()) { 
	     savedTeams.get(key).clear(); 
	    } 
	     
	    Bukkit.getServer().broadcastMessage(Messages.PREFIX.replace("UHC", "TEAM") + "All teams has been cleared.");
	    for(Player online : Bukkit.getOnlinePlayers()) {
	    	online.sendTitle("§c§lTEAM", "§7All teams has been cleared.");
	    }
	   } else { 
	    sendHelp(sender); 
	   } 
	   return true; 
	  } 
	   
	  if (args[0].equalsIgnoreCase("color")) { 
	   if (!sender.hasPermission("uhc.team")) { 
	    sendHelp(sender); 
	    return true; 
	   } 
	    
	   Bukkit.getServer().broadcastMessage(Messages.PREFIX.replace("UHC", "TEAM") + "All teams has been re-colored."); 
	   for(Player online : Bukkit.getOnlinePlayers()) {
		   online.sendTitle("§c§lTEAM", "§7All teams has been §ar§be§c-§dc§eo§fl§1o§2r§3e§4d§f.");
	   }
	   teams.setup();
	   return true; 
	  } 
	   
	  if (args[0].equalsIgnoreCase("list")) { 
	   if (teams.getTeamsWithPlayers().size() == 0) { 
	    sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "There are no teams."); 
	    return true; 
	   } 
	    
	   sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "List of teams:"); 
	    
	   for (Team team : teams.getTeamsWithPlayers()) { 
	    StringBuilder list = new StringBuilder(""); 
	    int i = 1; 
	     
	    for (String entry : team.getEntries()) { 
	     if (list.length() > 0) { 
	      if (i == team.getEntries().size()) { 
	       list.append(" and "); 
	      } else { 
	       list.append(", "); 
	      } 
	     } 
	      
	     list.append(entry); 
	     i++; 
	    } 
	     
	    sender.sendMessage(team.getPrefix() + team.getName() + ": §7" + list.toString().trim() + "."); 
	   } 
	   return true; 
	  } 
	  	   
	  sendHelp(sender); 
	  return true; 
	 } 
	  
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) { 
	  ArrayList<String> toReturn = new ArrayList<String>(); 
	      
	  if (args.length == 1) { 
	         ArrayList<String> types = new ArrayList<String>(); 
	         types.add("create"); 
	         types.add("invite"); 
	         types.add("kick"); 
	         types.add("accept"); 
	         types.add("deny"); 
	         types.add("info"); 
	         types.add("list"); 
	          
	         if (sender.hasPermission("uhc.team")) { 
	          types.add("clear"); 
	          types.add("add"); 
	          types.add("remove"); 
	          types.add("delete"); 
	          types.add("friendlyfire"); 
	         } 
	          
	         if (args[0].equals("")) { 
	          for (String type : types) { 
	           toReturn.add(type); 
	          } 
	         } else { 
	          for (String type : types) { 
	           if (type.toLowerCase().startsWith(args[0].toLowerCase())) { 
	            toReturn.add(type); 
	           } 
	          } 
	         } 
	        } 
	   
	  if (args.length == 2) { 
	         if (args[0].equalsIgnoreCase("add")) { 
	          if (args[1].equals("")) { 
	           for (Team teams : Teams.getInstance().getTeams()) { 
	            toReturn.add(teams.getName()); 
	           } 
	          } else { 
	           for (Team teams : Teams.getInstance().getTeams()) { 
	            if (teams.getName().toLowerCase().startsWith(args[1].toLowerCase())) { 
	             toReturn.add(teams.getName()); 
	            } 
	           } 
	          } 
	         } 
	         else if (args[0].equalsIgnoreCase("delete")) { 
	          if (args[1].equals("")) { 
	           for (Team teams : Teams.getInstance().getTeams()) { 
	            toReturn.add(teams.getName()); 
	           } 
	          } else { 
	           for (Team teams : Teams.getInstance().getTeams()) { 
	            if (teams.getName().toLowerCase().startsWith(args[1].toLowerCase())) { 
	             toReturn.add(teams.getName()); 
	            } 
	           } 
	          } 
	         } 
	         else if (args[0].equalsIgnoreCase("friendlyfire")) { 
	          toReturn.add("true"); 
	          toReturn.add("false"); 
	         } else { 
	          if (args[1].equals("")) { 
	           for (Player online : Bukkit.getOnlinePlayers()) { 
	            toReturn.add(online.getName()); 
	           } 
	          } else { 
	           for (Player online : Bukkit.getOnlinePlayers()) { 
	            if (online.getName().toLowerCase().startsWith(args[1].toLowerCase())) { 
	             toReturn.add(online.getName()); 
	            } 
	           } 
	          } 
	         } 
	        } 
	   
	  if (args.length == 3) { 
	         if (args[0].equalsIgnoreCase("add")) { 
	          if (args[2].equals("")) { 
	           for (Player online : Bukkit.getOnlinePlayers()) { 
	            toReturn.add(online.getName()); 
	           } 
	          } else { 
	           for (Player online : Bukkit.getOnlinePlayers()) { 
	            if (online.getName().toLowerCase().startsWith(args[2].toLowerCase())) { 
	             toReturn.add(online.getName()); 
	            } 
	           } 
	          } 
	         } 
	        } 
	   
	     return toReturn; 
	 } 
	  
	 /**
	  * Sends the help list to a player. 
	  *  
	  * @param sender the player. 
	  */ 
	 public void sendHelp(CommandSender sender) { 
	  sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "Team help:"); 
	  sender.sendMessage(Messages.ERROR + "§7/pm <message> - Talk in team chat."); 
	  sender.sendMessage(Messages.ERROR + "§7/sendcoords - Tell your coords to your teammates."); 
	  sender.sendMessage(Messages.ERROR + "§7/team info - Display your team info."); 
	  sender.sendMessage(Messages.ERROR + "§7/team list - List all teams."); 
	   
	   if (Settings.getData().get("teamsize.teammode").equals("team")) { 
	   sender.sendMessage(Messages.ERROR + "§7/team create - Create a team."); 
	   sender.sendMessage(Messages.ERROR + "§7/team leave - Leave your team."); 
	   sender.sendMessage(Messages.ERROR + "§7/team invite <player> - Invite a player to your team."); 
	   sender.sendMessage(Messages.ERROR + "§7/team kick <player> - Kick a player to your team."); 
	   sender.sendMessage(Messages.ERROR + "§7/team accept <player> - Accept the players request."); 
	   sender.sendMessage(Messages.ERROR + "§7/team deny <player> - Deny the players request."); 
	  } 
	   
	  if (sender.hasPermission("uhc.command.team")) { 
	   sender.sendMessage(Messages.PREFIX.replace("UHC", "TEAM") + "Team admin help:"); 
	   sender.sendMessage(Messages.ERROR + "§7/team info <player> - Display the targets team info."); 
	   sender.sendMessage(Messages.ERROR + "§7/team add <team> <player> - Add a player to a team."); 
	   sender.sendMessage(Messages.ERROR + "§7/team remove <player> - Remove a player from his team."); 
	   sender.sendMessage(Messages.ERROR + "§7/team delete <team> - Empty a specific team."); 
	   sender.sendMessage(Messages.ERROR + "§7/team friendlyfire <true|false> - Toggle FriendlyFire."); 
	   sender.sendMessage(Messages.ERROR + "§7/team clear - Clear all teams."); 
	  } 
	 }
}
