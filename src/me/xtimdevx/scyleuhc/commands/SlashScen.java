package me.xtimdevx.scyleuhc.commands;

import me.xtimdevx.scyleuhc.Main;
import me.xtimdevx.scyleuhc.Settings;
import me.xtimdevx.scyleuhc.utils.Messages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SlashScen implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("scenario")) {
			p.sendMessage(Messages.PREFIX.replace("UHC", "SCENARIO") + "Scenario's §8(§c" + Main.scenarios.size() + "§8)");
			if(Settings.getData().getBoolean("scenario.cutclean") == true) {
				p.sendMessage(Messages.PREFIX.replace("UHC", "CUTCLEAN") + "Ores and food are pre-melted.");
			}
			if(Settings.getData().getBoolean("scenario.timebomb") == true) {
				p.sendMessage(Messages.PREFIX.replace("UHC", "TIMEBOMB") + "If someone dies his loot spawns in a chest, you have 30 seconds before it explodes.");
			}
		}
		return true;
	}

}
