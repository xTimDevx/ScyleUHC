package me.xtimdevx.scyleuhc;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Game {
	
	public static void sendAction(Player player, String msg) {
		CraftPlayer craft = (CraftPlayer) player;
		
		IChatBaseComponent actionJSON = ChatSerializer.a("{text:'" + msg + "'}");
		PacketPlayOutChat actionPacket = new PacketPlayOutChat(actionJSON, (byte) 2);
		
		craft.getHandle().playerConnection.sendPacket(actionPacket);
	}
	
	public static void removeEffects() {
		for(Player online : Bukkit.getOnlinePlayers()) {
			online.removePotionEffect(PotionEffectType.JUMP);
			online.removePotionEffect(PotionEffectType.BLINDNESS);
			online.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
			online.removePotionEffect(PotionEffectType.SLOW_DIGGING);
			online.removePotionEffect(PotionEffectType.SLOW);
			online.removePotionEffect(PotionEffectType.INVISIBILITY);
			online.setMaxHealth(20);
		}
	}
}
