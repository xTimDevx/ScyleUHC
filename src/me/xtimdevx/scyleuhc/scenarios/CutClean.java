package me.xtimdevx.scyleuhc.scenarios;

import me.xtimdevx.scyleuhc.Settings;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class CutClean implements Listener{
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if(Settings.getData().getBoolean("scenario.cutclean") == true) {
			Entity en = e.getEntity();
			if(en.getType() == EntityType.CHICKEN) {
				for(ItemStack drops : e.getDrops()) {
					if(drops.getType() == Material.RAW_CHICKEN) {
						drops.setType(Material.COOKED_CHICKEN);
					}
				}
			}
			if(en.getType() == EntityType.COW) {
				for(ItemStack drops : e.getDrops()) {
					if(drops.getType() == Material.RAW_BEEF) {
						drops.setType(Material.COOKED_BEEF);
					}
				}
			}
			if(en.getType() == EntityType.SHEEP) {
				for(ItemStack drops : e.getDrops()) {
					if(drops.getType() == Material.MUTTON) {
						drops.setType(Material.COOKED_MUTTON);
					}
				}
			}
			if(en.getType() == EntityType.PIG) {
				for(ItemStack drops : e.getDrops()) {
					if(drops.getType() == Material.PORK) {
						drops.setType(Material.GRILLED_PORK);
					}
				}
			}
			if(en.getType() == EntityType.CHICKEN) {
				for(ItemStack drops : e.getDrops()) {
					if(drops.getType() == Material.RAW_CHICKEN) {
						drops.setType(Material.COOKED_CHICKEN);
					}
				}
			}
			if(en.getType() == EntityType.RABBIT) {
				for(ItemStack drops : e.getDrops()) {
					if(drops.getType() == Material.RABBIT) {
						drops.setType(Material.COOKED_RABBIT);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if(e.getBlock().getWorld().equals(Bukkit.getWorld("UHC"))) {
			return;
		}
		
		if(Settings.getData().getBoolean("scenario.cutclean") == true) {
			if(e.isCancelled()) {
				return;
			}
			
			if(e.getPlayer().getGameMode() != GameMode.SURVIVAL) {
				return;
			}
			
			Block b = e.getBlock();
			Location clone = new Location(b.getWorld(), 
			        b.getLocation().getBlockX() + 0.5D, b.getLocation().getBlockY(), 
			        b.getLocation().getBlockZ() + 0.5D);
		      if (b.getType() == Material.IRON_ORE) {
		        b.setType(Material.AIR);
		        b.getState().update();
		        b.getWorld().dropItemNaturally(clone, new ItemStack(Material.IRON_INGOT));
		        ((ExperienceOrb)b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(1);
		      }
		      else if ((b.getType() == Material.STONE) && (b.getData() > 0)) {
		        b.setType(Material.AIR);
		        b.getState().update();
		        
		        b.getWorld().dropItemNaturally(clone, (e.getPlayer().getItemInHand() != null) && (e.getPlayer().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) ? new ItemStack(Material.STONE) : new ItemStack(Material.COBBLESTONE));
		      }
		      else if (b.getType() == Material.GOLD_ORE) {
		    	  if(Settings.getData().getBoolean("scenario.aurophobia") == true) {
		    		  return;
		    	  }
		        b.setType(Material.AIR);
		        b.getState().update();
		        ((ExperienceOrb)b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
		        b.getWorld().dropItemNaturally(clone, new ItemStack(Material.GOLD_INGOT));
		      }
		      else if (b.getType() == Material.GRAVEL) {
		        b.setType(Material.AIR);
		        b.getState().update();
		        b.getWorld().dropItemNaturally(clone, new ItemStack(Material.FLINT));
		      }
		}
	}
}
