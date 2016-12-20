package me.xtimdevx.scyleuhc;

public enum State {
	
	ingame, scattering, lobby, closed, pregame, BANAAN;
	
	private static State currentState;
	
	public static void setState(State state) {
		currentState = state;
	}
	
	public static boolean isState(State state) {
		return currentState == state;
	}
	
	public static State getState() {
		return currentState;
	}
}
