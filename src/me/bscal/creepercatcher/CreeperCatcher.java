package me.bscal.creepercatcher;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.bscal.creepercatcher.item.CaptureEgg;
import me.bscal.creepercatcher.listener.ProjectileLaunchListener;

public class CreeperCatcher extends JavaPlugin{
	
	private CaptureEgg setup;
	private CatcherManager catcherManager;
	private static CreeperCatcher main;
	
	public void onEnable() {
		main = this;
		
		setup = new CaptureEgg();
		setup.setupRecipe();
		
		catcherManager = new CatcherManager();
		
		DataManager config = new DataManager(this, "config.yml", "config.yml");
		
		getCommand("creepercatcher").setExecutor(new CommandManager());
		
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new ProjectileLaunchListener(), this);
	}
	
	public void onDisable() {
		Bukkit.resetRecipes();
	}
	
	/**
	 * returns plugin
	 * @return
	 */
	public static Plugin getPlugin() {
		return Bukkit.getPluginManager().getPlugin("CreeperCatcher");
	}
	
	public static CreeperCatcher getMainInstance() {
		return main;
	}
	
	public CatcherManager getCatcherManager() {
		return catcherManager;
	}
	
}
