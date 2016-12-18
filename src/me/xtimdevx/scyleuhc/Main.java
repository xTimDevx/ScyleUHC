package me.xtimdevx.scyleuhc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.xtimdevx.scyleuhc.commands.SlashConfig;
import me.xtimdevx.scyleuhc.commands.SlashList;
import me.xtimdevx.scyleuhc.commands.SlashScatter;
import me.xtimdevx.scyleuhc.commands.SlashScen;
import me.xtimdevx.scyleuhc.commands.SlashSetstate;
import me.xtimdevx.scyleuhc.commands.SlashSpawn;
import me.xtimdevx.scyleuhc.commands.SlashTeam;
import me.xtimdevx.scyleuhc.commands.SlashTeleport;
import me.xtimdevx.scyleuhc.commands.SlashUHC;
import me.xtimdevx.scyleuhc.commands.SlashWhitelist;
import me.xtimdevx.scyleuhc.events.DeathEvents;
import me.xtimdevx.scyleuhc.events.ServerListPingEvents;
import me.xtimdevx.scyleuhc.scenarios.Timebomb;
import me.xtimdevx.scyleuhc.utils.ScoreBoard;
import me.xtimdevx.scyleuhc.utils.Teams;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public static Map<String, List<String>> scenarios = new HashMap<>();

	
	public static Plugin plugin;
	public static int teamlimit;
	public static int finalheal;
	public static int pvp;
	public static int border1;
	public static int border2;
	public static int border3;
	
	public void onEnable() {
		registerListeners();
		registerCommands();
		plugin = this;
		Settings.getInstance().setup(plugin);
		teamlimit = Settings.getData().getInt("teamsize.teamlimit");
		finalheal = Settings.getData().getInt("timer.finalheal");
		pvp = Settings.getData().getInt("timer.pvp");
		border1 = Settings.getData().getInt("timer.border.1");
		border2 = Settings.getData().getInt("timer.border.2");
		border3 = Settings.getData().getInt("timer.border.3");
		Teams.getInstance().setup();
		ScoreBoard.getInstance().setup();
		State.setState(State.closed);
		registerScenario();
	}
	
	public void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new Timebomb(this), this);
		Bukkit.getPluginManager().registerEvents(new DeathEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new ServerListPingEvents(this), this);
	}
	
	public void registerCommands() {
		getCommand("uhc").setExecutor(new SlashUHC());
		getCommand("scatter").setExecutor(new SlashScatter());
		getCommand("whitelist").setExecutor(new SlashWhitelist());
		getCommand("config").setExecutor(new SlashConfig());
		getCommand("team").setExecutor(new SlashTeam());
		getCommand("setstate").setExecutor(new SlashSetstate());
		getCommand("spawn").setExecutor(new SlashSpawn());
		getCommand("list").setExecutor(new SlashList());
		getCommand("scenario").setExecutor(new SlashScen());
		getCommand("teleport").setExecutor(new SlashTeleport());
	}
	
	public void registerScenario() {
		if(Settings.getData().getBoolean("scenario.cutclean") == true) {
			scenarios.put("cutclean", null);
		}
		if(Settings.getData().getBoolean("scenario.timebomb") == true) {
			scenarios.put("timebomb", null);
		}
	}
	
}
