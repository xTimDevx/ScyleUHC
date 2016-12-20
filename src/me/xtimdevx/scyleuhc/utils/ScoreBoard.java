package me.xtimdevx.scyleuhc.utils;

import me.xtimdevx.scyleuhc.Main;
import me.xtimdevx.scyleuhc.Settings;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreBoard {

	private static ScoreBoard manager = new ScoreBoard(); 
	 
	 public Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard(); 
	 public Objective nameHealth = board.getObjective("nameHealth"); 
	 public Objective tabHealth = board.getObjective("tabHealth"); 
	 public Objective hearts = board.getObjective("hearts"); 
	 public Objective kills = board.getObjective("kills"); 
	  
	 /**
	  * Gets the instance of the class. 
	  *  
	  * @return the instance. 
	  */ 
	 public static ScoreBoard getInstance() { 
	  return manager; 
	 } 
	  
	 /**
	  * Setup the scoreboard objectives. 
	  */ 
	 public void setup() { 
	  if (board.getObjective("kills") == null) { 
	   kills = board.registerNewObjective("kills", "playerKillCount"); 
	  } 
	   
	  if (board.getObjective("tabHealth") == null) { 
	   tabHealth = board.registerNewObjective("tabHealth", "health"); 
	  } 
	   
	  if (board.getObjective("nameHealth") == null) { 
	   nameHealth = board.registerNewObjective("nameHealth", "health"); 
	  } 
	   
	  if (board.getObjective("hearts") == null) { 
	   hearts = board.registerNewObjective("hearts", "health"); 
	  } 
	   
	   kills.setDisplayName("§8[§cUHC§8]: §7" + Settings.getData().get("game.host") + "§r");  
	
	   kills.setDisplaySlot(DisplaySlot.SIDEBAR);  
	   
	  nameHealth.setDisplaySlot(DisplaySlot.BELOW_NAME); 
	  nameHealth.setDisplayName("§c♥"); 
	 
	   tabHealth.setDisplaySlot(DisplaySlot.PLAYER_LIST); 
	  Main.plugin.getLogger().info("Scoreboards has been setup."); 
	 } 
	  
	 /**
	  * Sets the score of the given player. 
	  *  
	  * @param player the player setting it for. 
	  * @param newScore the new score. 
	  */ 
	 public void setScore(String player, int newScore) { 
	  Score score = kills.getScore(player); 
	   
	  score.setScore(newScore); 
	 } 
	 
	 /**
	  * Gets a score for the given string. 
	  *  
	  * @param string the wanted string. 
	  * @return The score of the string. 
	  */ 
	 public int getScore(String string) { 
	  return kills.getScore(string).getScore(); 
	 } 
	 
	 /**
	  * Reset the score of the given string. 
	  *  
	  * @param string the string resetting. 
	  */ 
	 public void resetScore(String string) { 
	  board.resetScores(string); 
	 } 
	}
