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
					online.sendMessage(Messages.PREFIX + "Enabled scenario's: §8(§c" + Main.scenarios.size() + "§8) §7" + list.toString());
				}
				
				GameTimer.runTimer();
			}
		}, 300L);
	}

}
