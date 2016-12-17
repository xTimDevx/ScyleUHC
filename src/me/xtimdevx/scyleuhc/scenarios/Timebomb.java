package me.xtimdevx.scyleuhc.scenarios;

import java.util.Arrays;

import me.xtimdevx.scyleuhc.Main;
import me.xtimdevx.scyleuhc.Settings;
import me.xtimdevx.scyleuhc.State;
import me.xtimdevx.scyleuhc.utils.Messages;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Timebomb implements Listener{
	
	public Timebomb(Main main) {
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		
		ItemStack goldenHead = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta gMeta = goldenHead.getItemMeta();
		gMeta.setDisplayName("§8[§cUHC§8]: §7Golden Head");
		gMeta.setLore(Arrays.asList(new String[] { "§8[§cUHC§8]: §7This golden apple heals §c4 §7hearts." }));
		goldenHead.setItemMeta(gMeta);
		
		if(Settings.getData().getBoolean("scenario.timebomb") == true) {
			if(!e.getEntity().getWorld().equals(Bukkit.getWorld("UHC"))) {
				return;
			}
			if(!State.isState(State.ingame)) {
				return;
			}
			final Player player = e.getEntity().getPlayer();
			
			e.setKeepInventory(true);
			final Location loc = player.getLocation().add(0, -1, 0);
			
			loc.getBlock().setType(Material.CHEST);
			loc.getBlock().getState().update(true);
			Chest chest = (Chest) loc.getBlock().getState();
			
			Location lo = loc.clone().add(0, 0, -1);
			lo.getBlock().setType(Material.CHEST);
			lo.getBlock().getState().update(true);
			
			for(ItemStack item : player.getInventory().getContents()) {
				if(item == null) {
					continue;
				}
				chest.getInventory().addItem(item);
			}
			
			for(ItemStack item : player.getInventory().getArmorContents()) {
				if(item == null) {
					continue;
				}
				chest.getInventory().addItem(item);
			}
			chest.getInventory().addItem(goldenHead);
			
			Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
				public void run() {
					for(Player online : Bukkit.getOnlinePlayers()) {
						online.sendMessage(Messages.PREFIX.replace("UHC", "TIMEBOMB") + "§c" + player.getName() + " §7corpse has exploded.");
					}
					loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 10, false, true);
					loc.getWorld().strikeLightning(loc);
				}
			}, 600);
			Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
				public void run() {
					loc.getWorld().strikeLightning(loc);
				}
			}, 620);
		}
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		if(Settings.getData().getBoolean("scenario.timebomb") == true) {
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
		}
	}
}
