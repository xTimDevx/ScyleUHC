package me.xtimdevx.scyleuhc.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.xtimdevx.scyleuhc.Game;
import me.xtimdevx.scyleuhc.Main;
import me.xtimdevx.scyleuhc.Settings;
import me.xtimdevx.scyleuhc.State;
import me.xtimdevx.scyleuhc.utils.Messages;
import me.xtimdevx.scyleuhc.utils.ScatterUtils;
import me.xtimdevx.scyleuhc.utils.ScoreBoard;
import me.xtimdevx.scyleuhc.utils.Teams;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

public class SlashScatter implements CommandExecutor{
	
	public static final HashMap<String, Location> scatterLocs = new HashMap<String, Location>(); 
	 public static boolean isReady = true; 
	 
	 @Override 
	 public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) { 
	  if (!sender.hasPermission("scylemc.uhc.command.scatter")) { 
	   sender.sendMessage(Messages.NOPERMMESSAGE); 
	   return true; 
	  } 
	   
	  if (args.length < 3) { 
	   sender.sendMessage(Messages.ERROR + "Usage: /scatter <radius> <teamscatter> <player|*>"); 
	   return true; 
	  } 
	  
	   
	  if (Bukkit.getWorld("UHC") == null) { 
	   sender.sendMessage(Messages.ERROR + "There are no worlds called UHC."); 
	   return true; 
	  } 
	   
	  final boolean teams; 
	  final int radius; 
	   
	  try { 
	   radius = Integer.parseInt(args[0]); 
	  } catch (Exception e) { 
	   sender.sendMessage(Messages.ERROR + "Invaild radius."); 
	   return true; 
	  } 
	   
	  if (args[1].equalsIgnoreCase("true")) { 
	   teams = true; 
	  } 
	  else if (args[1].equalsIgnoreCase("false")) { 
	   teams = false; 
	  }  
	  else { 
	   sender.sendMessage(Messages.ERROR + "Teamspread must be true of false");  
	   return true; 
	  } 
	   
	  if (args[2].equalsIgnoreCase("*")) { 
	   State.setState(State.scattering);
	   isReady = false; 
	    
	   int t = 0; 
	   int s = 0; 
	    
	   if (Settings.getData().get("teamsize.teammode").equals("team") && Settings.getData().getInt("teamsize.limit") > 1) { 
	    for (OfflinePlayer whitelisted : Bukkit.getServer().getWhitelistedPlayers()) { 
	     if (ScoreBoard.getInstance().board.getEntryTeam(whitelisted.getName()) == null) { 
	      Team team = Teams.getInstance().findAvailableTeam(); 
	       
	      if (team != null) { 
	       team.addEntry(whitelisted.getName()); 
	      } 
	     } 
	    } 
	   } 
	    
	   for (Team te : Teams.getInstance().getTeams()) { 
	    if (te.getSize() > 0) { 
	     if (te.getSize() > 1) { 
	      t++; 
	     } else { 
	      s++;
	     }
	    }
	   }
	    for (OfflinePlayer whitelisted : Bukkit.getServer().getWhitelistedPlayers()) { 
	    	if(ScoreBoard.getInstance().board.getEntryTeam(whitelisted.getName()) == null) {
	    			s++;
	    	}
	    }
	  
	   final int te = t; 
	   final int so = s; 
	    
	   if (teams) { 
	    Bukkit.getServer().broadcastMessage(Messages.PREFIX.replace("UHC", "SCATTER") + "Scattering §c" + t + " §7teams and §c" + s + " §7solos.");
	    for(Player online : Bukkit.getOnlinePlayers()) {
		    Game.sendAction(online, "§8[§cSCATTER§8]: §7Scattering §c" + t + " §7teams and §c" + s + " §7solos.");	
	    }
	   } else { 
		Bukkit.getServer().broadcastMessage(Messages.PREFIX + "Scattering §c" + Bukkit.getServer().getWhitelistedPlayers().size() + " §7players.");
	    for(Player online : Bukkit.getOnlinePlayers()) {
		    Game.sendAction(online, "§8[§cSCATTER§8]: §7Scattering §c" + Bukkit.getServer().getWhitelistedPlayers().size() + " §7players.");	
	    }
	   }
	    
	   for (Player online : Bukkit.getOnlinePlayers()) { 
	    online.playSound(online.getLocation(), Sound.FIREWORK_LAUNCH, 1, 1); 
	   } 
	    
	   new BukkitRunnable() { 
	    @SuppressWarnings("deprecation")
		public void run() { 
	    	Bukkit.getServer().broadcastMessage(Messages.PREFIX.replace("UHC", "SCATTER") + "Finding scatter locations."); 
		    for(Player online : Bukkit.getOnlinePlayers()) {
			    Game.sendAction(online, "§8[§cSCATTER§8]: §7Finding scatter locations.");	
		    }
	     for (Player online : Bukkit.getOnlinePlayers()) { 
	      online.playSound(online.getLocation(), Sound.NOTE_BASS, 1, 1); 
	     } 
	      
	     if (teams) { 
		      List<Location> loc = ScatterUtils.getScatterLocations(Bukkit.getWorld("UHC"), radius, te + so); 
	       
	      int index = 0; 
	       
	      for (Team tem : Teams.getInstance().getTeamsWithPlayers()) { 
	       for (String player : tem.getEntries()) { 
	        scatterLocs.put(player, loc.get(index)); 
	         
	        Bukkit.getOfflinePlayer(player).setWhitelisted(true); 
	       } 
	       index++; 
	      } 
	       
	      
	      for (OfflinePlayer online : Bukkit.getServer().getWhitelistedPlayers()) { 
	       if (ScoreBoard.getInstance().board.getEntryTeam(online.getName()) == null) { 
	        scatterLocs.put(online.getName(), loc.get(index)); 
	        index++; 
	       } 
	      } 
	     } else { 
		      List<Location> loc = ScatterUtils.getScatterLocations(Bukkit.getWorld("UHC"), radius, Bukkit.getServer().getWhitelistedPlayers().size()); 
	      
	      int index = 0; 
	       
	      for (OfflinePlayer online : Bukkit.getServer().getWhitelistedPlayers()) { 
	       scatterLocs.put(online.getName(), loc.get(index)); 
	       index++; 
	      } 
	     } 
	    } 
	   }.runTaskLater(Main.plugin, 30); 
	 
	   new BukkitRunnable() { 
	    public void run() { 
	    	Bukkit.getServer().broadcastMessage(Messages.PREFIX.replace("UHC", "SCATTER") + "Locations found, started loading chunks.");
		    for(Player online : Bukkit.getOnlinePlayers()) {
			    Game.sendAction(online, "§8[§cSCATTER§8] §7Locations found, started loading chunks.");	
		    }
	     for (Player online : Bukkit.getOnlinePlayers()) { 
	      online.playSound(online.getLocation(), Sound.NOTE_BASS, 1, 1); 
	     } 
	      
	     final ArrayList<Location> locs = new ArrayList<Location>(scatterLocs.values()); 
	     final ArrayList<String> names = new ArrayList<String>(scatterLocs.keySet()); 
	      
	     new BukkitRunnable() { 
	      int i = 0; 
	       
	      public void run() { 
	       if (i < locs.size()) { 
	        if (sender instanceof Player) { 
	         Player player = (Player) sender; 
	         player.teleport(locs.get(i)); 
	        } else { 
	         locs.get(i).getChunk().load(true); 
	        } 
	        i++; 
	       } else { 
	        cancel(); 
	        locs.clear(); 
	        Bukkit.getServer().broadcastMessage(Messages.PREFIX.replace("UHC", "SCATTER") + "All chunks loaded, starting scatter."); 
		    for(Player online : Bukkit.getOnlinePlayers()) {
			    Game.sendAction(online, "§8[§cSCATTER§8]: §7All chunks loaded, starting scatter.");	
		    }
	        for (Player online : Bukkit.getOnlinePlayers()) { 
	         online.playSound(online.getLocation(), Sound.NOTE_BASS, 1, 1); 
	        } 
	         
	        new BukkitRunnable() { 
	         int i = 0; 
	          
	         public void run() { 
	          if (i < names.size()) { 
	           Player scatter = Bukkit.getServer().getPlayer(names.get(i)); 
	            
	           if (scatter == null) { 
	           } else { 
	            scatter.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1726272000, 128)); 
	            scatter.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1726272000, 6)); 
	            scatter.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1726272000, 6)); 
	            scatter.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 1726272000, 10)); 
	            scatter.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1726272000, 6)); 
	            scatter.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1726272000, 2)); 
	            scatter.teleport(scatterLocs.get(names.get(i)));
	            for(Player online : Bukkit.getOnlinePlayers()) {
	            	Game.sendAction(online, "§8[§cSCATTER§8]: §7Scattered §c" + names.get(i) + "§7.");
	            }
	            scatterLocs.remove(names.get(i)); 
	           } 
	           i++; 
	          } else { 
	        	  Bukkit.getServer().broadcastMessage(Messages.PREFIX.replace("UHC", "SCATTER") + "The scatter has finished.");
	      	    for(Player online : Bukkit.getOnlinePlayers()) {
	    		    Game.sendAction(online, "§8[§cSCATTER§8] §7The scatter has finished.");	
	    	    }
	           isReady = true; 
	           names.clear(); 
	           cancel(); 
	            
	           for (Player online : Bukkit.getOnlinePlayers()) { 
	            online.playSound(online.getLocation(), Sound.FIREWORK_TWINKLE, 1, 1); 
	           } 
	          } 
	         } 
	        }.runTaskTimer(Main.plugin, 40, 3); 
	       } 
	      } 
	     }.runTaskTimer(Main.plugin, 5, 5); 
	    } 
	   }.runTaskLater(Main.plugin, 60); 
	  } else { 
	   final Player target = Bukkit.getPlayer(args[2]); 
	    
	   if (target == null) { 
	    sender.sendMessage(Messages.ERROR + "That player is not online."); 
	    return true; 
	   } 
	 
	   Bukkit.getServer().broadcastMessage(Messages.PREFIX.replace("UHC", "SCATTER") + "Scattering §c" + target.getName() + "§7."); 
	 
	   new BukkitRunnable() { 
	    public void run() { 
	    	Bukkit.getServer().broadcastMessage(Messages.PREFIX.replace("UHC", "SCATTER") + "Finding scatter location."); 
	      
	     if (teams) { 
	      if (target.getScoreboard().getEntryTeam(target.getName()) == null) { 
		       List<Location> loc = ScatterUtils.getScatterLocations(Bukkit.getWorld("UHC"), radius, 1); 
	       scatterLocs.put(target.getName(), loc.get(0)); 
	       return; 
	      } 
	       
	      Team tem = target.getScoreboard().getEntryTeam(target.getName()); 
	       
	      for (String tm : tem.getEntries()) { 
	       Player temmate = Bukkit.getServer().getPlayer(tm); 
	        
	       if (temmate != null) { 
	        scatterLocs.put(target.getName(), temmate.getLocation()); 
	        break; 
	       } 
	      } 
	     } else { 
		      List<Location> loc = ScatterUtils.getScatterLocations(Bukkit.getWorld("UHC"), radius, 1); 
	      scatterLocs.put(target.getName(), loc.get(0)); 
	     } 
	    } 
	   }.runTaskLater(Main.plugin, 30); 
	 
	   new BukkitRunnable() { 
	    public void run() { 
	    	Bukkit.getServer().broadcastMessage(Messages.PREFIX.replace("UHC", "SCATTER") + "Location found, scattering."); 
	      
	     if (!target.isOnline()) { 
	    	 Bukkit.getServer().broadcastMessage(Messages.PREFIX.replace("UHC", "SCATTER") + "§c" + target.getName() + " §7is offline, scheduled."); 
	     } else { 
	      if (State.isState(State.scattering)) { 
	       target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1726272000, 128)); 
	       target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1726272000, 6)); 
	       target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1726272000, 6)); 
	       target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 1726272000, 10)); 
	       target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1726272000, 6)); 
	       target.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1726272000, 2)); 
	      } 
	       
	      target.setWhitelisted(true); 
	      target.teleport(scatterLocs.get(target.getName())); 
	      Bukkit.getServer().broadcastMessage(Messages.PREFIX.replace("UHC", "SCATTER") + "§c" + target.getName() + " §7has been late-scattered."); 
	      scatterLocs.remove(target.getName()); 
	     } 
	    } 
	   }.runTaskLater(Main.plugin, 60); 
	  } 
	  return true; 
	 } 
	}

