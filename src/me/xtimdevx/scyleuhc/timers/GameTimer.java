package me.xtimdevx.scyleuhc.timers;

import me.xtimdevx.scyleuhc.Game;
import me.xtimdevx.scyleuhc.Main;
import me.xtimdevx.scyleuhc.Settings;
import me.xtimdevx.scyleuhc.commands.SlashUHC;
import me.xtimdevx.scyleuhc.utils.Messages;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameTimer {
	
	public static void runTimer() {
	final ItemStack beef = new ItemStack(Material.COOKED_BEEF, 10);
	
	Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
		public void run() {
			for(Player online : Bukkit.getOnlinePlayers()) {
				for(int i=0; i < 100; i ++) {
					online.sendMessage(" ");
				}
				online.sendMessage("§8§m]------------------------[§r");
				online.sendMessage(Messages.PREFIX.replace("UHC", "HOST") + Settings.getData().get("game.host"));
				online.sendMessage(Messages.PREFIX.replace("UHC", "TIMER") + "Final heal: §c" + Main.finalheal + "m");
				online.sendMessage(Messages.PREFIX.replace("UHC", "TIMER") + "PvP: §c" + Main.pvp + "m");
				online.sendMessage("§8§m]------------------------[§r");
			}
			Game.removeEffects();
		}
	}, 0L);
	Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
		public void run() {
			for(Player online : Bukkit.getOnlinePlayers()) {
				online.getInventory().addItem(beef);
				online.setHealth(20);
				online.setFoodLevel(20);
				online.setFireTicks(0);
				if(Settings.getData().get("game.host").equals(online.getName()) || SlashUHC.opspectators.containsKey(online.getName())) {
        			online.setGameMode(GameMode.SPECTATOR);
        		}else{
        			online.setGameMode(GameMode.SURVIVAL);
        		}
				online.sendMessage(Messages.PREFIX + "The game is now §c0 §7minutes in.");
			}
        	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "timer cancel");
        	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "timer " + Main.pvp*60 + " &8[§cTIMER§8]: &fTime until pvp: §c");
		}
	}, 0L);
	Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
		public void run() {
			for(Player online : Bukkit.getOnlinePlayers()) {
				online.sendMessage(Messages.PREFIX + "Everyone has been §chealed§7, no more heals will be given.");
				online.setHealth(20);
			}
		}
	}, Main.finalheal*20*60L);
	
	Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
		public void run() {
			for(Player online : Bukkit.getOnlinePlayers()) {
				online.sendMessage(Messages.PREFIX + "§cPvP §7is now on, be careful!");
				Bukkit.getWorld("UHC").setPVP(true);
			}
		}
	}, Main.pvp*20*60L);
	Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
		public void run() {
			for(Player online : Bukkit.getOnlinePlayers()) {
				online.sendMessage(Messages.PREFIX + "The game is now §c10 §7minutes in.");
			}
		}
	}, 12000L);
	Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
		public void run() {
			for(Player online : Bukkit.getOnlinePlayers()) {
				online.sendMessage(Messages.PREFIX + "The game is now §c20 §7minutes in.");
			}
		}
	}, 24000L);
	Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
		public void run() {
			for(Player online : Bukkit.getOnlinePlayers()) {
				online.sendMessage(Messages.PREFIX + "The game is now §c30 §7minutes in.");
			}
		}
	}, 36000L);
	Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
		public void run() {
			for(Player online : Bukkit.getOnlinePlayers()) {
				online.sendMessage(Messages.PREFIX + "The game is now §c40 §7minutes in.");
			}
		}
	}, 48000L);
	Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
		public void run() {
			for(Player online : Bukkit.getOnlinePlayers()) {
				online.sendMessage(Messages.PREFIX + "The game is now §c50 §7minutes in.");
			}
		}
	}, 60000L);
	Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
		public void run() {
			for(Player online : Bukkit.getOnlinePlayers()) {
				online.sendMessage(Messages.PREFIX + "The game is now §c60 §7minutes in.");
			}
		}
	}, 72000L);
	}

}
