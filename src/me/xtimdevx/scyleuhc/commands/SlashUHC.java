package me.xtimdevx.scyleuhc.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.xtimdevx.scyleuhc.Settings;
import me.xtimdevx.scyleuhc.State;
import me.xtimdevx.scyleuhc.timers.GameTimer;
import me.xtimdevx.scyleuhc.utils.Messages;
import me.xtimdevx.scyleuhc.utils.ScoreBoard;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SlashUHC implements CommandExecutor{
	
	public static Map<String, List<String>> opspectators = new HashMap<>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("uhc")) {
			if(!p.hasPermission("uhc.command.uhc")) {
				p.sendMessage(Messages.NOPERMMESSAGE);
				return true;
			}
			
			if(args.length == 0) {
				p.sendMessage(Messages.PREFIX + "UHC Help:");
				p.sendMessage(Messages.ERROR + "Usage: /uhc host");
				p.sendMessage(Messages.ERROR + "Usage: /uhc host <player>");
				p.sendMessage(Messages.ERROR + "Usage: /uhc spec");
				p.sendMessage(Messages.ERROR + "Usage: /uhc spec <player>");
				p.sendMessage(Messages.ERROR + "Usage: /uhc nextstate");
				p.sendMessage(Messages.ERROR + "Usage: /uhc end");
				p.sendMessage(Messages.ERROR + "Usage: /uhc state");
				p.sendMessage(Messages.ERROR + "Usage: /uhc pregen");
				p.sendMessage(Messages.ERROR + "Usage: /uhc info");
				p.sendMessage(Messages.ERROR + "Usage: /uhc world <seed>");
			}
			
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("host")) {
					if(Settings.getData().get("game.host").equals(p.getName())) {
						Settings.getData().set("game.host", "none");
						Settings.getInstance().saveData();
						ScoreBoard.getInstance().setup();
						p.sendMessage(Messages.PREFIX + "You are no longer in the mode §chost§7.");
						return true;
					}
					for(Player online : Bukkit.getOnlinePlayers()) {
						online.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "§c" + p.getName() + " §7is now in the mode §chost§7.");
					}
					Settings.getData().set("game.host", p.getName());
					Settings.getInstance().saveData();
					ScoreBoard.getInstance().setup();
				}
				if(args[0].equalsIgnoreCase("spec")) {
					if(opspectators.containsKey(p.getName())) {
						opspectators.remove(p.getName());
						p.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "You are no longer in the mode §chost§7.");
						return true;
					}
					for(Player online : Bukkit.getOnlinePlayers()) {
						online.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "§c" + p.getName() + " §7is now in the mode §cspec§7.");
					}
					opspectators.put(p.getName(), null);
					}
				if(args[0].equalsIgnoreCase("nextstate")) {
					if(!State.isState(State.closed) && !State.isState(State.ingame) && !State.isState(State.lobby) && !State.isState(State.pregame) && !State.isState(State.scattering)) {
						p.sendMessage(Messages.ERROR + "The state system has an error in it! Please contact Tim.");
						return true;
					}
					if(State.isState(State.closed)) {
						State.setState(State.lobby);
						for(Player online : Bukkit.getOnlinePlayers()) {
							online.sendMessage(Messages.PREFIX + "Opened the server.");
						}
						Bukkit.getServer().setWhitelist(false);
						p.sendMessage(Messages.PREFIX + "Next state: §cpregame, §7/uhc nextstate.");
						return true;
					}
					if(State.isState(State.lobby)) {
						State.setState(State.pregame);
						double x0 = -545.5;
				        double y0 = 15;
				        double z0 = -554.5;
				        org.bukkit.Location loc0 = new org.bukkit.Location(Bukkit.getWorld("HUB"), x0, y0, z0);
				        loc0.setYaw(90F);
				        loc0.setPitch(0F);
						for(Player online : Bukkit.getOnlinePlayers()) {
							online.sendMessage(Messages.PREFIX + "Prepering the game to start.");
							online.getInventory().clear();
							online.getInventory().setArmorContents(null);
							online.setHealth(20);
							online.setFoodLevel(20);
							online.teleport(loc0);
						}
						Bukkit.getWorld("UHC").setPVP(false);
						Bukkit.getWorld("UHC").setDifficulty(Difficulty.EASY);
						Bukkit.getWorld("UHC").setThundering(false);
						Bukkit.getWorld("UHC").setTime(0);
						p.performCommand("wl clear");
						p.performCommand("wl all");
						Bukkit.getServer().setWhitelist(true);
						p.sendMessage(Messages.PREFIX + "Next state: §cscattering, §7/uhc nextstate.");
						return true;
					}
					if(State.isState(State.pregame)) {
						if(Settings.getData().get("teamsize.teammode").equals("ffa")) {
							p.performCommand("scatter 990 false *");
						}
						if(Settings.getData().get("teamsize.teammode").equals("truelove")) {
							p.performCommand("scatter 990 false *");
						}
						if(Settings.getData().get("teamsize.teammode").equals("team")) {
							p.performCommand("scatter 990 true *");
						}
						p.sendMessage(Messages.PREFIX + "Next state: §cingame, §7/uhc nextstate.");
						return true;
					}
					if(State.isState(State.scattering)) {
						GameTimer.runTimer();
						return true;
					}
				}
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("host")) {
					@SuppressWarnings("deprecation")
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
					Player otarget = Bukkit.getPlayer(args[1]);
					if(otarget == null) {
						if(Settings.getData().get("game.host").equals(target.getName())) {
							p.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "§c" + target.getName() + " §7is no longer in the mode §chost§7.");
							Settings.getData().set("game.host", "none");
							Settings.getInstance().saveData();
							ScoreBoard.getInstance().setup();
							return true;
						}
						for(Player online : Bukkit.getOnlinePlayers()) {
							online.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "§c" + target.getName() + " §7is now in the mode §chost§7. §8(§coffline§8)");
						}
						Settings.getData().set("game.host", target.getName());
						Settings.getInstance().saveData();
						ScoreBoard.getInstance().setup();
					}else{
						if(Settings.getData().get("game.host").equals(target.getName())) {
							p.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "§c" + target.getName() + " §7is no longer in the mode §chost§7.");
							otarget.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "You are no longer in the mode §chost§7.");
							Settings.getData().set("game.host", "none");
							Settings.getInstance().saveData();
							ScoreBoard.getInstance().setup();
							return true;
						}
						for(Player online : Bukkit.getOnlinePlayers()) {
							online.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "§c" + otarget.getName() + " §7is now in the mode §chost§7.");
						}
						Settings.getData().set("game.host", otarget.getName());
						Settings.getInstance().saveData();
						ScoreBoard.getInstance().setup();
					}
				}
				if(args[0].equalsIgnoreCase("spec")) {
					@SuppressWarnings("deprecation")
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
					Player otarget = Bukkit.getPlayer(args[1]);
					if(otarget == null) {
						if(opspectators.containsKey(target.getName())) {
							opspectators.remove(target.getName());
							p.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "§c" + target.getName() + " §7is no longer in the mode §cspec§7.");
							return true;
						}
						for(Player online : Bukkit.getOnlinePlayers()) {
							online.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "§c" + target.getName() + " §7is now the mode §cspec §8(§coffline§8)");
						}
						opspectators.put(target.getName(), null);
					}else{
						if(opspectators.containsKey(target.getName())) {
							opspectators.remove(target.getName());
							p.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "§c" + target.getName() + " §7is no longer in the mode §cspec§7.");
							otarget.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "You are no longer in the mode §cspec§7.");
							otarget.setGameMode(GameMode.SURVIVAL);
							return true;
						}
						for(Player online : Bukkit.getOnlinePlayers()) {
							online.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "§c" + otarget.getName() + " §7is now the mode §cspec");
						}
						opspectators.put(otarget.getName(), null);
						otarget.setGameMode(GameMode.SPECTATOR);
					}
				}
			}
		   }
		return true;
	}
}
