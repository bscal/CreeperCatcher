package me.bscal.creepercatcher;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class CatcherManager {

	private ArrayList<Player> catchers = new ArrayList<Player>();
	
	public CatcherManager() {}
	
	public ArrayList<Player> getCatchers() {
		return catchers;
	}
	
}
