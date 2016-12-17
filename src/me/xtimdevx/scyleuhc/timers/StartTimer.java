package me.xtimdevx.scyleuhc.timers;

import me.xtimdevx.scyleuhc.Main;
import me.xtimdevx.scyleuhc.Settings;
import me.xtimdevx.scyleuhc.utils.Messages;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StartTimer {
	
	
	public static void runTimer(final Player sender) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			public void run() {
				for(Player online : Bukkit.getOnlinePlayers()) {
					online.sendMessage(Messages.PREFIX + "The game is starting in §c60 §7seconds.");
				}
				sender.performCommand("timer 60 §8[§cTIMER§8]: §fTime until start:§c");
			}
		}, 0L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			public void run() {
				for(Player online : Bukkit.getOnlinePlayers()) {
					online.sendMessage(Messages.PREFIX + "This game is hosted by: §c" + Settings.getData().get("game.host"));
				}
			}
		}, 100L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			public void run() {
				for(Player online : Bukkit.getOnlinePlayers()) {
					online.sendMessage(Messages.PREFIX + "The game is starting in §c50 §7seconds.");
				}
			}
		}, 200L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			public void run() {
				int i = 1;
				StringBuilder list = new StringBuilder();
				if(list.length() > 0) {
					if(i == Main.scenarios.size()) {
						list.append("§7 and ");
					}else{
						list.append("§7,");
					}
				}
				
				list.append(Main.scenarios.toString());
				i++;
				for(Player online : Bukkit.getOnlinePlayers()) {
					online.sendMessage(Messages.PREFIX + "Enabled scenario's: §8(§c" + Main.scenarios.size() + "§8) §7" + list.toString().replace("{", "").replace("}", "").replace("=null", ""));
				}
				
			}
		}, 300L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			public void run() {
				for(Player online : Bukkit.getOnlinePlayers()) {
					online.sendMessage(Messages.PREFIX + "The game is starting in §c40 §7seconds.");
				}
			}
		}, 400L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			public void run() {
				for(Player online : Bukkit.getOnlinePlayers()) {
					if(Settings.getData().get("teamsize.mode").equals("team")) {
						online.sendMessage(Messages.PREFIX + "This game is §ccTo" + Main.teamlimit);
					}
					if(Settings.getData().get("teamsize.mode").equals("ffa")) {
						online.sendMessage(Messages.PREFIX + "This game is §cFFA§7, Teaming will result in a §c1 §7week ban.");
					}
					if(Settings.getData().get("teamsize.mode").equals("random")) {
						online.sendMessage(Messages.PREFIX + "This game is §crTo§7" + Main.teamlimit);
					}
				}
			}
		}, 500L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			public void run() {
				for(Player online : Bukkit.getOnlinePlayers()) {
					online.sendMessage(Messages.PREFIX + "The game is starting in §c30 §7seconds.");
				}
			}
		}, 600L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			public void run() {
				for(Player online : Bukkit.getOnlinePlayers()) {
					online.sendMessage(Messages.PREFIX + "Good luck and have fun!");
				}
			}
		}, 700L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			public void run() {
				for(Player online : Bukkit.getOnlinePlayers()) {
					online.sendMessage(Messages.PREFIX + "The game is starting in §c20 §7seconds.");
				}
			}
		}, 900L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			public void run() {
				for(Player online : Bukkit.getOnlinePlayers()) {
					online.sendMessage(Messages.PREFIX + "The game is starting in §c10 §7seconds.");
				}
			}
		}, 1100L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			public void run() {
				for(Player online : Bukkit.getOnlinePlayers()) {
					online.sendMessage(Messages.PREFIX + "The game is starting in §c5 §7seconds.");
				}
			}
		}, 1200L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			public void run() {
				for(Player online : Bukkit.getOnlinePlayers()) {
					online.sendMessage(Messages.PREFIX + "The game is starting in §c4 §7seconds.");
				}
			}
		}, 1220L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				for(Player online : Bukkit.getOnlinePlayers()) {
					online.sendMessage(Messages.PREFIX + "The game is starting in §c3 §7seconds.");
					online.sendTitle("§8[§c3§8]:", "");
				}
			}
		}, 1240L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				for(Player online : Bukkit.getOnlinePlayers()) {
					online.sendMessage(Messages.PREFIX + "The game is starting in §c2 §7seconds.");
					online.sendTitle("§8[§c2§8]:", "");
				}
			}
		}, 1260L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				for(Player online : Bukkit.getOnlinePlayers()) {
					online.sendMessage(Messages.PREFIX + "The game is starting in §c1 §7seconds.");
					online.sendTitle("§8[§c1§8]:", "");
				}
			}
		}, 1280L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			public void run() {
				GameTimer.runTimer();
			}
		}, 1300L);
	}

}
