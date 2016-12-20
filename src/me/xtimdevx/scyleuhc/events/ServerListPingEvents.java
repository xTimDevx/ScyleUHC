package me.xtimdevx.scyleuhc.events;

import me.xtimdevx.scyleuhc.Main;
import me.xtimdevx.scyleuhc.Settings;
import me.xtimdevx.scyleuhc.State;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingEvents implements Listener{
	
	public ServerListPingEvents(Main main) {
	}

	@EventHandler
	public void onServerListPing(ServerListPingEvent e) {
		if(State.isState(State.closed)) {
			e.setMotd("§8[§cScyleMC§8]: §8[§c1.8.x§8]: §8[§c@ScyleMC§8]: §8[§cHost: " + Settings.getData().get("game.host") + "§8]: \n§8[§cSTATUS§8]: §7No game running, follow §c@ScyleMC§7.");
		}
		
		if(State.isState(State.lobby)) {
			e.setMotd("§8[§cScyleMC§8]: §8[§c1.8.x§8]: §8[§c@ScyleMC§8]: §8[§cHost: " + Settings.getData().get("game.host") + "§8]: \n§8[§cSTATUS§8]: §7The whitelist is off.");
		}
		
		if(State.isState(State.pregame)) {
			e.setMotd("§8[§cScyleMC§8]: §8[§c1.8.x§8]: §8[§c@ScyleMC§8]: §8[§cHost: " + Settings.getData().get("game.host") + "§8]: \n§8[§cSTATUS§8]: §7Preparing to start.");
		}
		
		if(State.isState(State.scattering)) {
			e.setMotd("§8[§cScyleMC§8]: §8[§c1.8.x§8]: §8[§c@ScyleMC§8]: §8[§cHost: " + Settings.getData().get("game.host") + "§8]: \n§8[§cSTATUS§8]: §7Scattering the players.");
		}
		
		if(State.isState(State.ingame)) {
			e.setMotd("§8[§cScyleMC§8]: §8[§c1.8.x§8]: §8[§c@ScyleMC§8]: §8[§cHost: " + Settings.getData().get("game.host") + "§8]: \n§8[§cSTATUS§8]: §7A game is already running.");
		}
	}

}
