package me.xtimdevx.scyleuhc.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class LocationUtils {
	
    public static int highestTeleportableYAtLocation(Location location) { 
    	
        Location startingLocation = location.clone(); 
        startingLocation.setY(location.getWorld().getMaxHeight()); 
 
        boolean above2WasAir = false; 
        boolean aboveWasAir = false; 
        Block currentBlock = startingLocation.getBlock(); 
 
        while (currentBlock.getY() >= 0) { 
 
            if (currentBlock.getType() != Material.AIR) { 
                if (above2WasAir && aboveWasAir) { 
                    return currentBlock.getY(); 
                } 
 
                above2WasAir = aboveWasAir; 
                aboveWasAir = false; 
            } else { 
                above2WasAir = aboveWasAir; 
                aboveWasAir = true; 
            } 
 
            currentBlock = currentBlock.getRelative(BlockFace.DOWN); 
        } 
 
        return -1; 
    } 

}
