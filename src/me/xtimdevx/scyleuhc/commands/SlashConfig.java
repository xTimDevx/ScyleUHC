package me.xtimdevx.scyleuhc.commands;

import me.xtimdevx.scyleuhc.Main;
import me.xtimdevx.scyleuhc.Settings;
import me.xtimdevx.scyleuhc.utils.Messages;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SlashConfig implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("config")) {
			if(!p.hasPermission("uhc.command.config")) {
				p.sendMessage(Messages.NOPERMMESSAGE);
				return true;
			}
			
			if(args.length == 0) {
				p.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "Game config:");
				p.sendMessage(Messages.ERROR + "/config scenario <scenario>");
				p.sendMessage(Messages.ERROR + "/config teamsize mode <mode>");
				p.sendMessage(Messages.ERROR + "/config teamsize limit <limit>");
				p.sendMessage(Messages.ERROR + "/config teamsize crossteaming");
				p.sendMessage(Messages.ERROR + "/config rates apple <int>");
				p.sendMessage(Messages.ERROR + "/config rates flint <int>");
				p.sendMessage(Messages.ERROR + "/config rates shears");
				p.sendMessage(Messages.ERROR + "/config timer finalheal <minutes>");
				p.sendMessage(Messages.ERROR + "/config timer pvp <minutes>");
			}
			
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("scenario")) {
					p.sendMessage(Messages.ERROR + "/config scenario <scenario>");
				}
				if(args[0].equalsIgnoreCase("teamsize")) {
					p.sendMessage(Messages.ERROR + "/config teamsize mode <mode>");
					p.sendMessage(Messages.ERROR + "/config teamsize limit <limit>");
					p.sendMessage(Messages.ERROR + "/config teamsize crossteaming");
				}
				if(args[0].equalsIgnoreCase("rates")) {
					p.sendMessage(Messages.ERROR + "/config rates apple <int>");
					p.sendMessage(Messages.ERROR + "/config rates flint <int>");
					p.sendMessage(Messages.ERROR + "/config rates shears");
				}
				if(args[0].equalsIgnoreCase("timer")) {
					p.sendMessage(Messages.ERROR + "/config timer finalheal <minutes>");
					p.sendMessage(Messages.ERROR + "/config timer pvp <minutes>");
				}
			}
			if(args.length == 2) {
				//Scenario's
				if(args[0].equalsIgnoreCase("scenario") && args[1].equalsIgnoreCase("cutclean")) {
					if(Settings.getData().getBoolean("scenario.cutclean") != true) {
						Settings.getData().set("scenario.cutclean", true);
						Settings.getInstance().saveData();
						Main.scenarios.put("cutclean", null);
						for(Player online : Bukkit.getOnlinePlayers()) {
							online.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "The scenario §ccutclean §7is now §cenabled§7.");
						}
					}else{
						Settings.getData().set("scenario.cutclean", false);
						Settings.getInstance().saveData();
						Main.scenarios.remove("cutclean");
						for(Player online : Bukkit.getOnlinePlayers()) {
							online.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "The scenario §ccutclean §7is now §cdisabled§7.");
						}
					}
					
				}
				if(args[0].equalsIgnoreCase("scenario") && args[1].equalsIgnoreCase("timebomb")) {
					if(Settings.getData().getBoolean("scenario.timebomb") != true) {
						Settings.getData().set("scenario.timebomb", true);
						Settings.getInstance().saveData();
						Main.scenarios.put("timebomb", null);
						for(Player online : Bukkit.getOnlinePlayers()) {
							online.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "The scenario §ctimebomb §7is now §cenabled§7.");
						}
					}else{
						Settings.getData().set("scenario.timebomb", false);
						Settings.getInstance().saveData();
						Main.scenarios.remove("timebomb");
						for(Player online : Bukkit.getOnlinePlayers()) {
							online.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "The scenario §ctimebomb §7is now §cdisabled§7.");
						}
					}
					
				}
			}
			if(args.length == 3) {
				if(args[0].equalsIgnoreCase("teamsize") && args[1].equalsIgnoreCase("limit")) {
					int i;
					
					try {
						i = Integer.parseInt(args[2]);
					} catch(Exception e) {
						p.sendMessage(Messages.ERROR + "This is not a valid number.");
						return true;
					}
					
					Main.teamlimit = i;
					for(Player online : Bukkit.getOnlinePlayers()) {
						online.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "Set the team §climit §7to §c" + i + "§7.");
					}
					Settings.getData().set("teamsize.teamlimit", i);
					Settings.getInstance().saveData();
				}
				if(args[0].equalsIgnoreCase("timer") && args[1].equalsIgnoreCase("finalheal")) {
					int i;
					
					try {
						i = Integer.parseInt(args[2]);
					} catch(Exception e) {
						p.sendMessage(Messages.ERROR + "This is not a valid number.");
						p.sendMessage(Messages.ERROR + "Correct usage: /config timer finalheal <minutes>");
						return true;
					}
					
					Main.finalheal = i;
					for(Player online : Bukkit.getOnlinePlayers()) {
						online.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "Set the §ctime §7for §cfinalheal §7at §c" + i + " §7minutes.");
					}
					Settings.getData().set("timer.finalheal", i);
					Settings.getInstance().saveData();
				}
				if(args[0].equalsIgnoreCase("timer") && args[1].equalsIgnoreCase("pvp")) {
					int i;
					
					try {
						i = Integer.parseInt(args[2]);
					} catch(Exception e) {
						p.sendMessage(Messages.ERROR + "This is not a valid number.");
						p.sendMessage(Messages.ERROR + "Correct usage: /config timer pvp <minutes>");
						return true;
					}
					
					Main.pvp = i;
					for(Player online : Bukkit.getOnlinePlayers()) {
						online.sendMessage(Messages.PREFIX.replace("UHC", "CONFIG") + "Set the §ctime §7for §cPvP §7at §c" + i + " §7minutes.");
					}
					Settings.getData().set("timer.pvp", i);
					Settings.getInstance().saveData();
				}
			}
		}
		return true;
	}

}
